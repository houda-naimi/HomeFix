package com.example.homefix_manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class TechnicianDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "technicians.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_TECHNICIANS = "technicians";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_EXPERIENCE = "experience";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_AVAILABLE = "available";
    public static final String COLUMN_CATEGORY = "category";

    public TechnicianDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_TECHNICIANS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_AGE + " INTEGER, " +
                COLUMN_EXPERIENCE + " INTEGER, " +
                COLUMN_PHONE + " TEXT, " +
                COLUMN_ADDRESS + " TEXT, " +
                COLUMN_AVAILABLE + " INTEGER, " +
                COLUMN_CATEGORY + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TECHNICIANS);
        onCreate(db);
    }

    public void addTechnician(Technician technician) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, technician.getName());
        values.put(COLUMN_AGE, technician.getAge());
        values.put(COLUMN_EXPERIENCE, technician.getExperience());
        values.put(COLUMN_PHONE, technician.getPhone());
        values.put(COLUMN_ADDRESS, technician.getAddress());
        values.put(COLUMN_AVAILABLE, technician.isAvailable() ? 1 : 0);
        values.put(COLUMN_CATEGORY, technician.getCategory());

        long id = db.insert(TABLE_TECHNICIANS, null, values);
        technician.setId((int) id);
        db.close();
    }


    public List<Technician> getTechniciansByCategory(String category) {
        List<Technician> technicians = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TECHNICIANS, null, COLUMN_CATEGORY + "=?",
                new String[]{category}, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Technician technician = new Technician(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AGE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EXPERIENCE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AVAILABLE)) == 1,
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY))
                );
                technicians.add(technician);
            }
            cursor.close();
        }
        db.close();
        return technicians;
    }

    public List<Technician> getAllTechnicians() {
        List<Technician> technicians = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TECHNICIANS, null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Technician technician = new Technician(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AGE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EXPERIENCE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AVAILABLE)) == 1,
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY))
                );
                technicians.add(technician);
            }
            cursor.close();
        }
        db.close();
        return technicians;
    }

    public void deleteTechnician(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TECHNICIANS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void updateTechnician(Technician technician) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, technician.getName());
        values.put(COLUMN_AGE, technician.getAge());
        values.put(COLUMN_EXPERIENCE, technician.getExperience());
        values.put(COLUMN_PHONE, technician.getPhone());
        values.put(COLUMN_ADDRESS, technician.getAddress());
        values.put(COLUMN_AVAILABLE, technician.isAvailable() ? 1 : 0);
        values.put(COLUMN_CATEGORY, technician.getCategory());

        db.update(TABLE_TECHNICIANS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(technician.getId())});
        db.close();
    }

    public Technician getTechnicianById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Technician technician = null;

        Cursor cursor = db.query(TABLE_TECHNICIANS, null, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            technician = new Technician(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AGE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EXPERIENCE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AVAILABLE)) == 1,
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY))
            );
            cursor.close();
        }

        db.close();
        return technician;
    }




}
