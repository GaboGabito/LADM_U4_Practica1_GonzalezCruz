package mx.edu.ittepic.ladm_u4_practica1_gonzalezcruz

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDatos(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int):
    SQLiteOpenHelper(context, name, factory, version){
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE TELEFONIA(CELULAR VARCHAR(200), MENSAJE VARCHAR(2000), NOMBRE VARCHAR(200), DESEADO VARCHAR(100))")
        db.execSQL("CREATE TABLE ENTRANTES(CELULAR VARCHAR(200))")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}