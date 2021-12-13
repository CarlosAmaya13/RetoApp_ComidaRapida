package com.example.reto2.datos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase sqLiteDatabase;


    public DBHelper(Context context) {
        super(context, "reto3.db", null, 1);
        sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE FAVORITOS(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME VARCHAR," +
                "IMAGE BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS FAVORITOS");
    }

    // Funciones personalizadas
    public void insertFavorito(String name, byte[] image){
        String sql = "INSERT INTO FAVORITOS VALUES(null, ?, ?)";
        SQLiteStatement statement = sqLiteDatabase.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindBlob(2, image);
        statement.executeInsert();
    }

    public Cursor getFavoritos(){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM FAVORITOS",null);
        return cursor;
    }

    public Cursor getFavoritoID(String id){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM FAVORITOS WHERE ID =" + id,null);
        return cursor;
    }

    public void deleteFavorito(String id){
        String[] args = new String[]{id};
        sqLiteDatabase.delete("FAVORITOS", "ID=?", args);
    }

    public void updateFavoritos(String id, String name, byte[] image){
        String sql = "UPDATE FAVORITOS " +
                "SET NAME = ?," +
                "IMAGE = ?" +
                "WHERE ID = "+ id;
        SQLiteStatement statement = sqLiteDatabase.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindBlob(2, image);
        statement.executeUpdateDelete();
    }



}
