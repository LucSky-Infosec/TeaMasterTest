

package com.example.teamaster.DB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.teamaster.Models.Account;

import java.util.ArrayList;

public class AccountDAO {
    // Constants for table name and column names
    public static final String TABLE_NAME = "Account";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_STAFF_ID = "staff_id";

    // Method to create the table for this entity
    public static String createTable() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT NOT NULL, " +
                COLUMN_PASSWORD + " TEXT NOT NULL, " +
                COLUMN_STATUS + " INTEGER NOT NULL, " +
                COLUMN_STAFF_ID + " INTEGER NOT NULL, " +
                "FOREIGN KEY(" + COLUMN_STAFF_ID + ") REFERENCES " + StaffDAO.TABLE_NAME + "(" + StaffDAO.COLUMN_ID + ")" +
                ");";
    }
    // Method to add a new account to the database
    public static void addAccount(Account account, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, account.getUsername());
        values.put(COLUMN_PASSWORD, account.getPassword());
        values.put(COLUMN_STATUS, account.getStatus() ? 1 : 0);
        values.put(COLUMN_STAFF_ID, account.getStaffID());
        db.insert(TABLE_NAME, null, values);
    }

    // Method to delete an account from the database
    public static void deleteAccount(int id, SQLiteDatabase db) {
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }
    // Method to update an account in the database
    public static void updateAccount(Account account, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, account.getUsername());
        values.put(COLUMN_PASSWORD, account.getPassword());
        values.put(COLUMN_STATUS, account.getStatus() ? 1 : 0);
        values.put(COLUMN_STAFF_ID, account.getStaffID());
        db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(account.getId())});
    }
    public static boolean isAccountExists(String username, SQLiteDatabase db) {
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};
        String limit = "1";
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
    public static void createDemoAccount(SQLiteDatabase db) {
        Account DemoAccount1 = new Account("1", "12345", true,  1);
        AccountDAO.addAccount(DemoAccount1, db);
        Account DemoAccount2 = new Account("2", "12345", true,  2);
        AccountDAO.addAccount(DemoAccount2, db);
        Account DemoAccount3 = new Account("3", "12345", true,  3);
        AccountDAO.addAccount(DemoAccount3, db);
    }
    public static Account getAccountByUsername(String username, SQLiteDatabase db) {
        String[] columns = {COLUMN_ID, COLUMN_USERNAME, COLUMN_PASSWORD, COLUMN_STATUS, COLUMN_STAFF_ID};
        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        Account account = null;
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));
            boolean status = (cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STATUS)) == 1);
            int staffID = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STAFF_ID));
            account = new Account(id, username, password, status, staffID);
        }
        cursor.close();
        return account;
    }

    public static Account getAccountById(int id, SQLiteDatabase db) {
        String[] columns = {COLUMN_ID, COLUMN_USERNAME, COLUMN_PASSWORD, COLUMN_STATUS, COLUMN_STAFF_ID};
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        Account account = null;
        if (cursor.moveToFirst()) {
            String username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));
            boolean status = (cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STATUS)) == 1);
            int staffID = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STAFF_ID));
            account = new Account(id, username, password, status, staffID);
        }
        cursor.close();
        return account;
    }


    public static ArrayList<Account> getAllAccounts(SQLiteDatabase db) {
        ArrayList<Account> accounts = new ArrayList<>();
        String[] columns = {COLUMN_ID, COLUMN_USERNAME, COLUMN_PASSWORD, COLUMN_STATUS, COLUMN_STAFF_ID};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));
            boolean status = (cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STATUS)) == 1);
            int staffID = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STAFF_ID));
            Account account = new Account(id, username, password, status, staffID);
            accounts.add(account);
        }
        cursor.close();
        return accounts;
    }
    public static Account getLatestAccount(SQLiteDatabase db) {
        String[] columns = {COLUMN_ID, COLUMN_USERNAME, COLUMN_PASSWORD, COLUMN_STATUS, COLUMN_STAFF_ID};
        String orderBy = COLUMN_ID + " DESC";
        String limit = "1";
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, orderBy, limit);
        Account account = null;
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));
            boolean status = (cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STATUS)) == 1);
            int staffID = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STAFF_ID));
            account = new Account(id, username, password, status, staffID);
        }
        cursor.close();
        return account;
    }



}