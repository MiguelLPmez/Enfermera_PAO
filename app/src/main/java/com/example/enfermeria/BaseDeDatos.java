package com.example.enfermeria;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class BaseDeDatos {
    private AdminSQLiteOpenHelper admin;
    private SQLiteDatabase baseDeDatos;

    public BaseDeDatos(String DBName, Context context){
        admin = new AdminSQLiteOpenHelper(context, DBName,null, 1);
        baseDeDatos = admin.getWritableDatabase();
    }

    /*          PARA TABLA DE NOTAS "notas"        */

    // Devuelve los títulos de todas las notas existentes en la base de datos.
    public String[] getTitulosNotas() {
        Cursor cursor;
        ArrayList<String> titulos;

        cursor = baseDeDatos.rawQuery("select titulo from notas", null);
        titulos = new ArrayList<>();

        cursor.moveToFirst();
        if(cursor.getCount() > 0)                              //Si existen registros de la consulta
            while (!cursor.isAfterLast()) {
                titulos.add(cursor.getString(0));
                cursor.moveToNext();
            }

        return titulos.toArray(new String[titulos.size()]);
    }

    //Devuelve el contenido de una nota.
    // null si la nota no fue encontrada.
    public String getContenidoNota(String titulo){
        Cursor cursor;
        cursor = baseDeDatos.
                rawQuery("select titulo, contenido from notas where titulo ='" + titulo + "'",
                        null);

        if(cursor.moveToFirst())
            return cursor.getString(1);
        else
            return null;
    }

    // Guarda una nueva nota dentro de la base de datos, o sobreescribe
    // el contenido de una existente que tenga el mismo titulo.
    public void guardarNota(String titulo, String contenido){
        ContentValues registro = new ContentValues();
        registro.put("titulo", titulo);
        registro.put("contenido", contenido);

        if(Arrays.asList(getTitulosNotas()).contains(titulo))
            baseDeDatos.update("notas", registro, "titulo='" + titulo + "'",
                    null);
        else
            baseDeDatos.insert("notas", null, registro);
    }

    // Elimina una nota dentro de la base de datos.
    public boolean eliminarNota(String titulo) {
        return  (baseDeDatos.delete("notas", "titulo='" + titulo + "'", null) == 1);
    }

    /*          PARA TABLA DE TAREAS POR REALIZAR "por_hacer"        */

    //Agrega una tarea
    public void agregarTareaNueva(){
        ContentValues registro = new ContentValues();

        registro.put("actividad", "Nueva tarea");
        registro.put("estatus", "0");

        baseDeDatos.insert("por_hacer", null, registro);
    }

    //Elimina la tabla por_hacer y crea una nueva con los datos
    public void updateToDoList(ArrayList<String[]> list){
        baseDeDatos.execSQL("DROP TABLE IF EXISTS " + "por_hacer");
        baseDeDatos.execSQL("create table if not exists por_hacer(codigo INTEGER primary key AUTOINCREMENT unique, actividad text, estatus text)");

        for (String[] tarea: list) {
            ContentValues registro = new ContentValues();
            registro.put("actividad", tarea[1]);
            registro.put("estatus", tarea[2]);
            baseDeDatos.insert("por_hacer", null, registro);
        }
    }

    //Devuelve la tabla de por_hacer.
    public ArrayList<String[]> getToDoList(){
        ArrayList<String[]> list;
        list = new ArrayList<String[]>();
        Cursor cursor;

        cursor = baseDeDatos.rawQuery("select * from por_hacer", null);

        cursor.moveToFirst();
        if(cursor.getCount() > 0)
            while (!cursor.isAfterLast()) {
                list.add(new String[] {
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2)});
                cursor.moveToNext();
            }

        return list;
    }

    public String[] getUltimaTarea(){
        Cursor cursor;
        cursor = baseDeDatos.rawQuery("select * from por_hacer",null);
        cursor.moveToLast();
        return new String[]{cursor.getString(0), cursor.getString(1), cursor.getString(2)};
    }

    //Actualizar una tarea
    public boolean actualizarTarea(int id, String actividad, String estatus){
        ContentValues registro = new ContentValues();

        registro.put("actividad", actividad);
        registro.put("estatus", estatus);

        return (baseDeDatos.update("por_hacer", registro, "codigo=" + id,null) == 1);
    }

    //Eliminar una tarea
    public boolean eliminarTarea(int id){
        return (baseDeDatos.delete("por_hacer","codigo=" + id, null) == 1);
    }

    //Devuelve la actividad de una tarea en por_hacer según su id
    public String getActividad(int id){
        Cursor cursor;
        cursor = baseDeDatos.rawQuery("select actividad from por_hacer where codigo = " + id,null);
        cursor.moveToFirst();
        return cursor.getString(0);
    }

    //Devuelve el estatus de una tarea en por_hacer según su id
    public String getEstatus(int id){
        Cursor cursor;
        cursor = baseDeDatos.rawQuery("select estatus from por_hacer where codigo = " + id,null);
        cursor.moveToFirst();
        return cursor.getString(0);
    }

    public int getID(String actividad){
        Cursor cursor;
        cursor = baseDeDatos.rawQuery("select codigo from por_hacer where actividad = '" + actividad + "'",null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

}
