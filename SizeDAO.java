package com.example.teamaster.DB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.teamaster.Models.Size;
import java.util.ArrayList;
public class SizeDAO {
    // Constants for table name and column names
    public static final String TABLE_NAME = "Size";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_SIZE_NAME = "size_name";

    // Method to create the table for this entity
    public static String createTable() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SIZE_NAME + " TEXT NOT NULL" +
                ");";
    }

    // Method to add a new size to the database
    public static void addSize(Size size, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_SIZE_NAME, size.getSizeName());
        db.insert(TABLE_NAME, null, values);
    }

    // Method to delete a size from the database
    public static void deleteSize(int id, SQLiteDatabase db) {
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    // Method to update a size in the database
    public static void updateSize(Size size, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_SIZE_NAME, size.getSizeName());
        db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(size.getId())});
    }

    // Method to get all sizes from the database
    public static ArrayList<Size> getAllSizes(SQLiteDatabase db) {
        ArrayList<Size> sizes = new ArrayList<>();
        String[] columns = {COLUMN_ID, COLUMN_SIZE_NAME};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String sizeName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SIZE_NAME));
            Size size = new Size(id, sizeName);
            sizes.add(size);
        }
        cursor.close();
        return sizes;
    }

    // Method to get a size by its ID from the database
    public static Size getSizeById(int id, SQLiteDatabase db) {
        String[] columns = {COLUMN_ID, COLUMN_SIZE_NAME};
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        Size size = null;
        if (cursor.moveToFirst()) {
            String sizeName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SIZE_NAME));
            size = new Size(id, sizeName);
        }
        cursor.close();
        return size;
    }

    // Method to check if a size exists in the database
    public static boolean isSizeExist(String sizeName, SQLiteDatabase db) {
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_SIZE_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{sizeName});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
    public static void createDemoSize(SQLiteDatabase db) {
        Size sizeS = new Size("S");
        SizeDAO.addSize(sizeS, db);
        Size sizeM = new Size("M");
        SizeDAO.addSize(sizeM, db);
        Size sizeL = new Size("L");
        SizeDAO.addSize(sizeL, db);
    }
}
