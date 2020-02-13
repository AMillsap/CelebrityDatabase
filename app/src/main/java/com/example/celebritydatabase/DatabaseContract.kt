package com.example.celebritydatabase

const val DATABASE_NAME = "data_per_database"
const val TABLE_NAME = "celebrity_table"
const val DATABASE_VERSION = 1
const val COL_FIRST_NAME = "first_name"
const val COL_LAST_NAME = "last_name"
const val COL_BIRTH_DATE = "birth_date"
const val COL_NET_WORTH = "net_worth"
const val COL_IS_FAVORITE = "is_favorite"

const val CREATE_CELEBRITY_TABLE =
    "CREATE TABLE $TABLE_NAME (" +
            "$COL_FIRST_NAME String," +
            "$COL_LAST_NAME String PRIMARY_KEY," +
            "$COL_BIRTH_DATE String," +
            "$COL_NET_WORTH String," +
            "$COL_IS_FAVORITE String)"
