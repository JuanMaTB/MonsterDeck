package com.juanma.feedback01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "bestiary.db";
    public static final int DB_VERSION = 1;

    public static final String T_MONSTER = "monsters";
    public static final String C_ID = "_id";
    public static final String C_NAME = "name";
    public static final String C_LEVEL = "level";
    public static final String C_DEFEATED = "defeated";
    public static final String C_TYPE = "type";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + T_MONSTER + " (" +
                C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                C_NAME + " TEXT NOT NULL, " +
                C_LEVEL + " INTEGER NOT NULL, " +
                C_DEFEATED + " INTEGER NOT NULL DEFAULT 0, " +
                C_TYPE + " TEXT NOT NULL" +
                ")";
        db.execSQL(sql);

        // Datos de demo (para que siempre veas algo al arrancar)
        insertMonster(db, "Slime", 1, false, "slime");
        insertMonster(db, "Goblin", 3, false, "goblin");
        insertMonster(db, "Dragon", 10, false, "dragon");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + T_MONSTER);
        onCreate(db);
    }

    private void insertMonster(SQLiteDatabase db, String name, int level, boolean defeated, String type) {
        ContentValues cv = new ContentValues();
        cv.put(C_NAME, name);
        cv.put(C_LEVEL, level);
        cv.put(C_DEFEATED, defeated ? 1 : 0);
        cv.put(C_TYPE, type);
        db.insert(T_MONSTER, null, cv);
    }

    public long addMonster(String name, int level, boolean defeated, String type) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(C_NAME, name);
        cv.put(C_LEVEL, level);
        cv.put(C_DEFEATED, defeated ? 1 : 0);
        cv.put(C_TYPE, type);
        return db.insert(T_MONSTER, null, cv);
    }

    public int updateMonster(long id, String name, int level, boolean defeated, String type) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(C_NAME, name);
        cv.put(C_LEVEL, level);
        cv.put(C_DEFEATED, defeated ? 1 : 0);
        cv.put(C_TYPE, type);
        return db.update(T_MONSTER, cv, C_ID + "=?", new String[]{String.valueOf(id)});
    }

    public int setDefeated(long id, boolean defeated) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(C_DEFEATED, defeated ? 1 : 0);
        return db.update(T_MONSTER, cv, C_ID + "=?", new String[]{String.valueOf(id)});
    }

    public int deleteMonster(long id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(T_MONSTER, C_ID + "=?", new String[]{String.valueOf(id)});
    }

    public Monster getMonster(long id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(T_MONSTER, null, C_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);

        Monster m = null;
        if (c.moveToFirst()) {
            m = fromCursor(c);
        }
        c.close();
        return m;
    }

    public ArrayList<Monster> getAll(boolean onlyDefeated) {
        SQLiteDatabase db = getReadableDatabase();
        String selection = onlyDefeated ? (C_DEFEATED + "=1") : null;

        Cursor c = db.query(T_MONSTER, null, selection, null, null, null, C_LEVEL + " ASC");

        ArrayList<Monster> list = new ArrayList<>();
        while (c.moveToNext()) {
            list.add(fromCursor(c));
        }
        c.close();
        return list;
    }

    private Monster fromCursor(Cursor c) {
        long id = c.getLong(c.getColumnIndexOrThrow(C_ID));
        String name = c.getString(c.getColumnIndexOrThrow(C_NAME));
        int level = c.getInt(c.getColumnIndexOrThrow(C_LEVEL));
        boolean defeated = c.getInt(c.getColumnIndexOrThrow(C_DEFEATED)) == 1;
        String type = c.getString(c.getColumnIndexOrThrow(C_TYPE));
        return new Monster(id, name, level, defeated, type);
    }
}
