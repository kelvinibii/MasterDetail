package com.example.kelvindu.masterdetail.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.kelvindu.masterdetail.model.PositionTable
import com.example.kelvindu.masterdetail.model.PostTable
import org.jetbrains.anko.db.*

/**
 * Created by KelvinDu on 10/17/2017.
 */
class MainDBHelper(ctx: Context): ManagedSQLiteOpenHelper(ctx, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.createTable(PostTable.TABLE_NAME, true,
                PostTable.ID to INTEGER + PRIMARY_KEY,
                PostTable.USER_ID to INTEGER,
                PostTable.TITLE to TEXT,
                PostTable.BODY to TEXT)
        db!!.createTable(PositionTable.TABLE_NAME, true,
                PositionTable.POST_ID to INTEGER,
                PositionTable.LATITUDE to TEXT,
                PositionTable.LONGITUDE to TEXT,
                PositionTable.TIMESTAMP to TEXT)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.dropTable(PostTable.TABLE_NAME)
        db!!.createTable(PostTable.TABLE_NAME, true,
                PostTable.ID to INTEGER + PRIMARY_KEY,
                PostTable.USER_ID to INTEGER,
                PostTable.TITLE to TEXT,
                PostTable.BODY to TEXT)
        db!!.createTable(PositionTable.TABLE_NAME, true,
                PositionTable.POST_ID to INTEGER,
                PositionTable.LATITUDE to TEXT,
                PositionTable.LONGITUDE to TEXT,
                PositionTable.TIMESTAMP to TEXT)
    }

    companion object {
        @JvmField
        val DB_VERSION = 4
        @JvmField
        val DB_NAME = "db_post"
    }
}