package com.example.rentalps.database;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import java.util.List;
import java.util.ArrayList;
import com.example.rentalps.models.Catatan;

public class CatatanDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "catatan.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_CATATAN = "catatan";

    public CatatanDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_CATATAN + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "isi TEXT, " +
                "tanggal TEXT, " +
                "hari TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATATAN);
        onCreate(db);
    }

    public void tambahCatatan(String isi, String tanggal, String hari) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("isi", isi);
        values.put("tanggal", tanggal);
        values.put("hari", hari);
        db.insert(TABLE_CATATAN, null, values);
        db.close();
    }
    public void hapusCatatan(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("catatan", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }



    public List<Catatan> getSemuaCatatan() {
        List<Catatan> catatanList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM catatan", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String isi = cursor.getString(cursor.getColumnIndex("isi"));
                String tanggal = cursor.getString(cursor.getColumnIndex("tanggal"));
                String hari = cursor.getString(cursor.getColumnIndex("hari"));
                catatanList.add(new Catatan(id, isi, tanggal, hari));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return catatanList;
    }


}
