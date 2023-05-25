package com.example.teamaster.DB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;

import com.example.teamaster.Models.Table;

import java.text.ParseException;
import java.util.ArrayList;

public class TableDAO {
    // Constants for table name and column names
    public static final String TABLE_NAME = "Table";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DATE_CREATED = "date_created";
    public static final String COLUMN_STATUS = "status";

    // Method to create the table for this entity
    public static String createTable() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_STATUS + " INTEGER NOT NULL, " +
                COLUMN_DATE_CREATED + " DATE NOT NULL" +
                ");";
    }

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    // Method to add a new table to the database
    public static void addTable(Table table, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, table.getTableName());
        values.put(COLUMN_STATUS, table.getStatus() ? 1 : 0);
        String DateCreated = dateFormat.format(table.getDateCreated());
        values.put(COLUMN_DATE_CREATED, DateCreated);
        db.insert(TABLE_NAME, null, values);
    }

    // Method to delete a table from the database
    public static void deleteTable(int id, SQLiteDatabase db) {
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    // Method to update a table in the database
    public static void updateTable(Table table, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, table.getTableName());
        values.put(COLUMN_STATUS, table.getStatus() ? 1 : 0);
        values.put(COLUMN_DATE_CREATED, dateFormat.format(table.getDateCreated()));
        db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(table.getId())});
    }

    // Method to check if a table with the given name already exists in the database
    public static boolean isTableExists(String name, SQLiteDatabase db) {
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_NAME + " = ?";
        String[] selectionArgs = {name};
        String limit = "1";
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    // Method to get all tables from the database
    public static ArrayList<Table> getAllTables(SQLiteDatabase db) throws ParseException {
        ArrayList<Table> tables = new ArrayList<>();
        String[] columns = {COLUMN_ID, COLUMN_NAME, COLUMN_STATUS, COLUMN_DATE_CREATED};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            @SuppressLint("Range") boolean status = cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS)) == 1;
            @SuppressLint("Range") String dateCreated = cursor.getString(cursor.getColumnIndex(COLUMN_DATE_CREATED));
            Table table = new Table(id, name, dateFormat.parse(dateCreated), status);
            tables.add(table);
        }

        cursor.close();
        return tables;
    }
    // Method to create some demo tables
    public static void createDemoTables(SQLiteDatabase db) throws ParseException {

        Table DemoTable1 = new Table("Ban 1", dateFormat.parse("01-04-2022"), true);
        TableDAO.addTable(DemoTable1, db);
        Table DemoTable2 = new Table("Ban 2", dateFormat.parse("02-04-2022"), true);
        TableDAO.addTable(DemoTable2, db);
        Table DemoTable3 = new Table("Ban 3", dateFormat.parse("03-04-2022"), true);
        TableDAO.addTable(DemoTable3, db);
    }
}

