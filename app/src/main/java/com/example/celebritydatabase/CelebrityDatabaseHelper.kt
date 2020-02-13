package com.example.celebritydatabase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class CelebrityDatabaseHelper(context: Context)
    :SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
{
    override fun onCreate(database: SQLiteDatabase?)
    {
        database?.execSQL(CREATE_CELEBRITY_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun insertCelebrityIntoDatabase(celebrity: Celebrity){
        val database = writableDatabase
        val contentValues = ContentValues()

        contentValues.put(COL_FIRST_NAME, celebrity.firstName)
        contentValues.put(COL_LAST_NAME, celebrity.lastName)
        contentValues.put(COL_BIRTH_DATE, celebrity.birthDate)
        contentValues.put(COL_NET_WORTH, celebrity.netWorth)
        contentValues.put(COL_IS_FAVORITE, celebrity.isFavorite)

        database.insert(TABLE_NAME, null, contentValues)
        database.close()

    }

    fun removePersonFromDatabase(lastName : String) {
        val database = writableDatabase
        database.delete(TABLE_NAME, "$COL_LAST_NAME = ?", arrayOf(lastName))
        database.close()
    }

    fun updatePersonInDatabase(celebrity: Celebrity) {
        val database = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_FIRST_NAME, celebrity.firstName)
        contentValues.put(COL_LAST_NAME, celebrity.lastName)
        contentValues.put(COL_BIRTH_DATE, celebrity.birthDate)
        contentValues.put(COL_NET_WORTH, celebrity.netWorth)
        contentValues.put(COL_IS_FAVORITE, celebrity.isFavorite)


        database.update(TABLE_NAME, contentValues, "$COL_LAST_NAME = ?", arrayOf(celebrity.lastName))
        database.close()
    }

    fun getAllPeopleFromDatabase() : ArrayList<Celebrity> {
        val database = readableDatabase
        var celebrityList : ArrayList<Celebrity> = ArrayList<Celebrity>()
        val cursor = database
            .rawQuery("SELECT * FROM $TABLE_NAME",
                null)

        if(cursor.moveToFirst()) {
            do {
                val firstName = cursor.getString(cursor.getColumnIndex(COL_FIRST_NAME))
                val lastName = cursor.getString(cursor.getColumnIndex(COL_LAST_NAME))
                val birthDate = cursor.getString(cursor.getColumnIndex(COL_BIRTH_DATE))
                val netWorth = cursor.getString(cursor.getColumnIndex(COL_NET_WORTH))
                val isFavorite = cursor.getString(cursor.getColumnIndex(COL_IS_FAVORITE))
                val celebrity = Celebrity(firstName, lastName, birthDate,netWorth,isFavorite)
                celebrityList.add(celebrity)
            } while(cursor.moveToNext())
        }
        cursor.close()
        database.close()
        return celebrityList
    }

    fun getAllFavoritesFromDatabase() : ArrayList<Celebrity>
    {
        val database = readableDatabase
        var celebrityList : ArrayList<Celebrity> = ArrayList<Celebrity>()
        val cursor = database
            .rawQuery("SELECT * FROM $TABLE_NAME WHERE $COL_IS_FAVORITE = 'true'",
                null)

        if(cursor.moveToFirst()) {
            do {
                val firstName = cursor.getString(cursor.getColumnIndex(COL_FIRST_NAME))
                val lastName = cursor.getString(cursor.getColumnIndex(COL_LAST_NAME))
                val birthDate = cursor.getString(cursor.getColumnIndex(COL_BIRTH_DATE))
                val netWorth = cursor.getString(cursor.getColumnIndex(COL_NET_WORTH))
                val isFavorite = cursor.getString(cursor.getColumnIndex(COL_IS_FAVORITE))
                val celebrity = Celebrity(firstName, lastName, birthDate,netWorth,isFavorite)
                celebrityList.add(celebrity)
                Log.d("TAG", isFavorite)
            } while(cursor.moveToNext())
        }
        cursor.close()
        database.close()
        return celebrityList
    }

    fun getOnePersonFromDatabase(lastName : String) : Celebrity? {
        val database = readableDatabase
        var celebrity : Celebrity? = null
        val cursor = database
            .rawQuery("SELECT * FROM $TABLE_NAME WHERE $COL_LAST_NAME = '$lastName'",
                null)

        if(cursor.moveToFirst()) {
            val firstName = cursor.getString(cursor.getColumnIndex(COL_FIRST_NAME))
            val lastName = cursor.getString(cursor.getColumnIndex(COL_LAST_NAME))
            val birthDate = cursor.getString(cursor.getColumnIndex(COL_BIRTH_DATE))
            val netWorth = cursor.getString(cursor.getColumnIndex(COL_NET_WORTH))
            val isFavorite = cursor.getString(cursor.getColumnIndex(COL_IS_FAVORITE))
            celebrity = Celebrity(firstName, lastName, birthDate, netWorth, isFavorite)
        }
        cursor.close()
        database.close()
        return celebrity
    }

}