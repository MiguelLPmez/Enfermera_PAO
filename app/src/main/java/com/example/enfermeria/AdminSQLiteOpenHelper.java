package com.example.enfermeria;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase BaseDeDatos) {
        BaseDeDatos.execSQL("create table if not exists notas(codigo int primary key, titulo text, contenido text)");
        BaseDeDatos.execSQL("create table if not exists por_hacer(codigo INTEGER primary key AUTOINCREMENT unique, actividad text, estatus text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
