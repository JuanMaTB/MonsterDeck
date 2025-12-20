package com.juanma.feedback01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/*
este archivo se encarga de toda la gestion de la base de datos sqlite
aqui creo la tabla, inserto datos, consulto, actualizo y borro

se relaciona con:
- mainactivity: para cargar la lista de monstruos
- detailactivity: para obtener un monstruo concreto
- editmonsteractivity: para crear o modificar un monstruo
- monster: modelo de datos que representa una fila de la tabla

nota:
tener toda la bd aqui evita mezclar logica de datos con pantallas
*/
public class DBHelper extends SQLiteOpenHelper {

    // nombre del archivo fisico de la base de datos
    public static final String DB_NAME = "bestiary.db";

    // version de la bd, si cambia se ejecuta onupgrade
    public static final int DB_VERSION = 1;

    // nombre de la tabla
    public static final String T_MONSTER = "monsters";

    // nombres de las columnas
    public static final String C_ID = "_id";
    public static final String C_NAME = "name";
    public static final String C_LEVEL = "level";
    public static final String C_DEFEATED = "defeated";
    public static final String C_TYPE = "type";

    // constructor normal, solo paso el contexto
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /*
    este metodo se ejecuta la primera vez que se crea la bd
    aqui creo la tabla y meto datos de ejemplo para no ver la app vacia
    */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // sql para crear la tabla de monstruos
        String sql = "CREATE TABLE " + T_MONSTER + " (" +
                C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                C_NAME + " TEXT NOT NULL, " +
                C_LEVEL + " INTEGER NOT NULL, " +
                C_DEFEATED + " INTEGER NOT NULL DEFAULT 0, " +
                C_TYPE + " TEXT NOT NULL" +
                ")";

        db.execSQL(sql);

        // datos de demo para que al arrancar siempre haya contenido
        insertMonster(db, "Slime", 1, false, "slime");
        insertMonster(db, "Goblin", 3, false, "goblin");
        insertMonster(db, "Dragon", 10, false, "dragon");
    }

    /*
    este metodo se llama si cambia la version de la bd
    para este feedback borro todo y vuelvo a crear
    */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + T_MONSTER);
        onCreate(db);
    }

    /*
    metodo privado para insertar desde oncreate
    lo separo para no repetir codigo
    */
    private void insertMonster(SQLiteDatabase db, String name, int level, boolean defeated, String type) {
        ContentValues cv = new ContentValues();
        cv.put(C_NAME, name);
        cv.put(C_LEVEL, level);
        cv.put(C_DEFEATED, defeated ? 1 : 0);
        cv.put(C_TYPE, type);
        db.insert(T_MONSTER, null, cv);
    }

    /*
    insertar un nuevo monstruo desde la pantalla de edicion
    */
    public long addMonster(String name, int level, boolean defeated, String type) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(C_NAME, name);
        cv.put(C_LEVEL, level);
        cv.put(C_DEFEATED, defeated ? 1 : 0);
        cv.put(C_TYPE, type);

        return db.insert(T_MONSTER, null, cv);
    }

    /*
    actualizar un monstruo existente
    uso el id para saber que fila modificar
    */
    public int updateMonster(long id, String name, int level, boolea
