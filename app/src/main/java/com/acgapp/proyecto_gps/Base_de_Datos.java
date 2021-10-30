package com.acgapp.proyecto_gps;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Base_de_Datos extends SQLiteOpenHelper {
    private static String NOMBRE_BASE="Cordenadas.db";
    private static String CREAR_TABLA="CREATE TABLE cordenadas("+
            "num_control TEXT," +
            "latitud TEXT," +
            "longitud TEXT," +
            "fecha TEXT)";
    private static  int DB_VERSION=1;
    public Base_de_Datos(Context context) {
        super(context,NOMBRE_BASE,null,DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAR_TABLA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE "+"cordenadas");
        onCreate(db);
    }
}
