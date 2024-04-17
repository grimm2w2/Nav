package com.example.nav

import android.content.ContentValues

import android.content.Context

import android.database.Cursor

import android.database.sqlite.SQLiteDatabase

import android.database.sqlite.SQLiteOpenHelper


class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :

    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {



    override fun onCreate(db: SQLiteDatabase) {



        val query = ("CREATE TABLE " + TABLE_NAME + " ("

                + ID_COL + " INTEGER PRIMARY KEY, " +

                GPS_COl + " TEXT," +

                NET_COL + " TEXT" + ")")



        db.execSQL(query)

    }


    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {


        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)

        onCreate(db)

    }



    fun addCoord(gps : String, net : String ){



        val values = ContentValues()


        values.put(GPS_COl, gps)

        values.put(NET_COL, net)



        val db = this.writableDatabase



        db.insert(TABLE_NAME, null, values)




        db.close()

    }




    fun getCoord(): Cursor? {



        val db = this.readableDatabase



        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)


    }
    fun delCoord(){
        val db = this.writableDatabase
        db.delete(TABLE_NAME, null, null)
    }


    companion object{


        private val DATABASE_NAME = "COORDINATES_ALL"



        private val DATABASE_VERSION = 1


        val TABLE_NAME = "coordinates"


        val ID_COL = "id"



        val GPS_COl = "gps"



        val NET_COL = "net"

    }
}