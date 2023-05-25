package com.example.teamaster.DB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.teamaster.Models.TableDetails;

import java.util.ArrayList;

public class TableDetailsDAO {
    // Constants for table name and column names
    public static final String TABLE_NAME = "TableDetails";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TABLE_ID = "table_id";
    public static final String COLUMN_BILL_ID = "bill_id";

    // Method to create the table for this entity
    public static String createTable() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TABLE_ID + " INTEGER NOT NULL, " +
                COLUMN_BILL_ID + " INTEGER NOT NULL, " +
                "FOREIGN KEY(" + COLUMN_TABLE_ID + ") REFERENCES " + TableDAO.TABLE_NAME + "(" + TableDAO.COLUMN_ID + ")" +
                "FOREIGN KEY(" + COLUMN_BILL_ID + ") REFERENCES " + BillDAO.TABLE_NAME + "(" + BillDAO.COLUMN_ID + ")" +
                ");";
    }

    // Method to add a new table detail to the database
    public static void addTableDetail(TableDetails tableDetails, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TABLE_ID, tableDetails.getTableID());
        values.put(COLUMN_BILL_ID, tableDetails.getBillID());
        db.insert(TABLE_NAME, null, values);
    }

    // Method to delete a table detail from the database
    public static void deleteTableDetail(int id, SQLiteDatabase db) {
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    // Method to update a table detail in the database
    public static void updateTableDetail(TableDetails tableDetails, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TABLE_ID, tableDetails.getTableID());
        values.put(COLUMN_BILL_ID, tableDetails.getBillID());
        db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(tableDetails.getId())});
    }

    public static boolean isTableDetailExist(String tableDetailId, SQLiteDatabase db) {
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{tableDetailId});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public static ArrayList<TableDetails> getAllTableDetails(SQLiteDatabase db) {
        ArrayList<TableDetails> tableDetailsList = new ArrayList<>();

        String[] columns = {COLUMN_ID, COLUMN_TABLE_ID, COLUMN_BILL_ID};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                int tableID = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TABLE_ID));
                int billID = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BILL_ID));

                TableDetails tableDetails = new TableDetails(id, tableID, billID);
                tableDetailsList.add(tableDetails);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tableDetailsList;
    }

    public static TableDetails getTableDetailById(int id, SQLiteDatabase db) {
        String[] columns = {COLUMN_ID, COLUMN_TABLE_ID, COLUMN_BILL_ID};
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        TableDetails tableDetails = null;
        if (cursor.moveToFirst()) {
            int tableID = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TABLE_ID));
            int billID = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BILL_ID));
            tableDetails = new TableDetails(id, tableID, billID);
        }
        cursor.close();
        return tableDetails;
    }
    public static void createDemoTableDetails(SQLiteDatabase db) {
        // Create some sample table details
        TableDetails tableDetails1 = new TableDetails(1, 1);
        TableDetails tableDetails2 = new TableDetails(2, 1);
        TableDetails tableDetails3 = new TableDetails(3, 2);

        // Add the sample table details to the database
        addTableDetail(tableDetails1, db);
        addTableDetail(tableDetails2, db);
        addTableDetail(tableDetails3, db);
    }

}