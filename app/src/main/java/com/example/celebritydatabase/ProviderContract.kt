package com.example.celebritydatabase

import android.content.ContentUris
import android.net.Uri
import android.provider.BaseColumns

const val CONTENT_AUTHORITY = "com.example.celebritydatabase"
val BASE_CONTENT_ID = Uri.parse("content://${CONTENT_AUTHORITY}")
const val PATH_CELEBRITY = "celebrity"
val CONTENT_URI = BASE_CONTENT_ID.buildUpon().appendPath(PATH_CELEBRITY).build()

class CelebrityEntry : BaseColumns
{
    //Static objects in Kotlin
    companion object
    {
        val CONTENT_TYPE = "vnd.android.cursor.dir/${CONTENT_URI}/${PATH_CELEBRITY}"
        val CONTENT_ITEM_TYPE = "vnd.android.cursor.item/${CONTENT_URI}/${PATH_CELEBRITY}"
        fun buildCelebrityUri(id : Long) = ContentUris.withAppendedId(CONTENT_URI, id)
    }

}