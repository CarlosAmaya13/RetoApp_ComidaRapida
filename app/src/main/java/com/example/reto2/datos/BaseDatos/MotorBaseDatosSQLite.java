package com.example.reto2.datos.BaseDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MotorBaseDatosSQLite extends SQLiteOpenHelper {

    public MotorBaseDatosSQLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /* ====================================================================================================== */
        //TABLA FAVORITOS
        db.execSQL("CREATE TABLE favoritos (id INT, titulo TEXT,descripcion TEXT)");
        //---- Registros
        /*
        db.execSQL("INSERT INTO favoritos VALUES (1, 'Vegetariana','sssssssssssss')");
        db.execSQL("INSERT INTO favoritos VALUES (2, 'Tres quesos','hhhhhhhhhhhhhhhhhh')");
        db.execSQL("INSERT INTO favoritos VALUES (3, 'Pollo Chanpiñones','eeeeeeeeeeeeeeeeee')");

         */

        /* ====================================================================================================== */
        //TABLA PRODUCTOS
        db.execSQL("CREATE TABLE productos (imagen INT, titulo TEXT,descripcion TEXT)");
        //---- Registros
        db.execSQL("INSERT INTO productos VALUES ( 0, 'Hawaiana','ttttttttttttttttttttttt')");
        db.execSQL("INSERT INTO productos VALUES ( 1, 'Napolitana','yyyyyyyyyyyyyyyyyyyyyyyyy')");
        db.execSQL("INSERT INTO productos VALUES ( 2, 'Peperoni','qqqqqqqqqqqqqqqqqqqqqqqqqqqqq')");

        /* ====================================================================================================== */
        //TABLA SERVICIOS
        db.execSQL("CREATE TABLE servicios (titulo TEXT,descripcion TEXT)");
        //---- Registros
        db.execSQL("INSERT INTO servicios VALUES ('Domicilios','wwwwwwwwwwwwwwwwwwwwwwwwwwww')");
        db.execSQL("INSERT INTO servicios VALUES ('Promociones','uuuuuuuuuuuuuuuuuuuuuuuuu')");
        db.execSQL("INSERT INTO servicios VALUES ('Puerta a Puerta','iiiiiiiiiiiiiiiiiiiiiiiiiiiiiii')");
        /* ====================================================================================================== */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE favoritos");
        db.execSQL("DROP TABLE productos");
        db.execSQL("DROP TABLE servicios");
        onCreate(db);

    }
}
