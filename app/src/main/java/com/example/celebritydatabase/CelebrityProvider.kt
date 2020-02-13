package com.example.celebritydatabase

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

const val CELEBRITY = 100
const val CELEBRITY_ITEM = 101
class CelebrityProvider : ContentProvider()
{
    val celebrityDatabaseHelper by lazy {CelebrityDatabaseHelper(context!!)}
    val uriMatcher by lazy {buildUriMatcher()}

    fun buildUriMatcher() : UriMatcher
    {
        val returnMatcher = UriMatcher(UriMatcher.NO_MATCH)
        returnMatcher.addURI(CONTENT_AUTHORITY, PATH_CELEBRITY, CELEBRITY)
        returnMatcher.addURI(CONTENT_AUTHORITY, "$PATH_CELEBRITY/#", CELEBRITY_ITEM)
        return returnMatcher

    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int
    {
        val database = celebrityDatabaseHelper.writableDatabase
        val rowsDeleted = database.delete(TABLE_NAME, "$COL_LAST_NAME = ?", selectionArgs)
        if(rowsDeleted > 0)
        {
            context?.contentResolver?.notifyChange(uri, null)
        }
        return rowsDeleted
    }

    override fun getType(uri: Uri): String?
    {
        return when(uriMatcher.match(uri))
        {
            CELEBRITY -> CelebrityEntry.CONTENT_TYPE
            CELEBRITY_ITEM -> CelebrityEntry.CONTENT_ITEM_TYPE
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri?
    {
        val database = celebrityDatabaseHelper.writableDatabase
        val id = database.insert(TABLE_NAME, null, values)
        return when
        {
            id > 0 -> completeInsert(id, uri)
            else -> throw UnsupportedOperationException("Unable to insert row $uri")
        }
    }

    private fun completeInsert(id : Long, uri : Uri) : Uri?
    {
        context?.contentResolver?.notifyChange(uri,null)
        return CelebrityEntry.buildCelebrityUri(id)

    }

    override fun onCreate(): Boolean
    {
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val database = celebrityDatabaseHelper.writableDatabase
        var returnCursor : Cursor? = null

        when(uriMatcher.match(uri))
        {
            CELEBRITY -> {
                returnCursor = database.query(TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder)
            }
            CELEBRITY_ITEM -> {
                val id = ContentUris.parseId(uri).toString()
                returnCursor = database.query(TABLE_NAME,
                    projection,
                    "$COL_LAST_NAME = ?",
                    arrayOf(id),
                    null,
                    null,
                    sortOrder)
            }
        }
        return returnCursor
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        val databaseHelper = celebrityDatabaseHelper.writableDatabase
        val rowsAffected = databaseHelper.update(TABLE_NAME, values, "$COL_LAST_NAME = ?", selectionArgs)
        if(rowsAffected > 0)
        {
            context?.contentResolver?.notifyChange(uri, null)
        }
        return rowsAffected
    }
}
