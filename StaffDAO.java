package com.example.teamaster.DB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.teamaster.Models.Staff;

import java.util.ArrayList;

public class StaffDAO {
    // Constants for table name and column names
    public static final String TABLE_NAME = "Staff";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_PHONE_NUMBER = "phone_number";
    public static final String COLUMN_ROLE = "role";
    public static final String COLUMN_GENDER = "gender";

    public static final String COLUMN_AVATAR = "avatar";
    public static final String COLUMN_ACCOUNT_ID = "account_id";

    // Method to create the table for this entity
    public static String createTable() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FIRST_NAME + " TEXT NOT NULL, " +
                COLUMN_LAST_NAME + " TEXT NOT NULL, " +
                COLUMN_PHONE_NUMBER + " TEXT NOT NULL, " +
                COLUMN_ROLE + " INTEGER NOT NULL, " +
                COLUMN_GENDER + " TEXT NOT NULL, " +
                COLUMN_AVATAR+ " TEXT NOT NULL, " +
                COLUMN_ACCOUNT_ID + " INTEGER NULL, " +
                "FOREIGN KEY(" + COLUMN_ACCOUNT_ID + ") REFERENCES " + AccountDAO.TABLE_NAME + "(" + AccountDAO.COLUMN_ID + ")" +
                ");";
    }

    // Method to add a new staff to the database
    public static void addStaff(Staff staff, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, staff.getFirstName());
        values.put(COLUMN_LAST_NAME, staff.getLastName());
        values.put(COLUMN_PHONE_NUMBER, staff.getPhoneNumber());
        values.put(COLUMN_ROLE, staff.getRole());
        values.put(COLUMN_GENDER, staff.getGender());
        values.put(COLUMN_AVATAR, staff.getAvatar());
//        values.put(COLUMN_ACCOUNT_ID, staff.getAccountID());  // 1 thông tin user liên kết với 1 account, không được thay đổi giá trị
        if (staff.getAccountID() != null) {
            values.put(COLUMN_ACCOUNT_ID, staff.getAccountID());
        } else {
            values.putNull(COLUMN_ACCOUNT_ID);
        }
        db.insert(TABLE_NAME, null, values);
    }

    // Method to delete a staff from the database
    public static void deleteStaff(int id, SQLiteDatabase db) {
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    // Method to update a staff in the database
    public static void updateStaff(Staff staff, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, staff.getFirstName());
        values.put(COLUMN_LAST_NAME, staff.getLastName());
        values.put(COLUMN_PHONE_NUMBER, staff.getPhoneNumber());
        values.put(COLUMN_ROLE, staff.getRole());
        values.put(COLUMN_GENDER, staff.getGender());
        values.put(COLUMN_AVATAR, staff.getAvatar());
        values.put(COLUMN_ACCOUNT_ID, staff.getAccountID());
        db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(staff.getId())});
    }
    public static boolean isStaffExist(String staffId, SQLiteDatabase db) {
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{staffId});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
//     Method to create a demo staff
    public static void createDemoStaff(SQLiteDatabase db) {
        Staff staffDemo1 = new Staff("Bão", "Nguyễn Hoài", "0123456789", 1, "Nam", "defaultAvatar", 1);
        StaffDAO.addStaff(staffDemo1, db);
        Staff staffDemo2 = new Staff("Huyền", "Tôn Thị Thu", "0123456789", 0, "Nữ", "defaultAvatar", 2);
        StaffDAO.addStaff(staffDemo2, db);
        Staff staffDemo3 = new Staff("Dương", "Vân Phi", "0123456789", 0, "Nam", "defaultAvatar", 3);
        StaffDAO.addStaff(staffDemo3, db);
    }
    public static Staff getStaffById(int id, SQLiteDatabase db) {
        String[] columns = {COLUMN_ID, COLUMN_FIRST_NAME, COLUMN_LAST_NAME, COLUMN_PHONE_NUMBER, COLUMN_ROLE, COLUMN_GENDER, COLUMN_AVATAR, COLUMN_ACCOUNT_ID};
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        Staff staff = null;
        if (cursor.moveToFirst()) {
            String firstName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME));
            String lastName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_NAME));
            String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE_NUMBER));
            int role = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ROLE));
            String gender = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER));
            String avatar = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AVATAR));
            int accountID = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ACCOUNT_ID));
            staff = new Staff(id, firstName, lastName, phoneNumber, role, gender, avatar, accountID);
        }
        cursor.close();
        return staff;
    }
    public static ArrayList<Staff> getAllStaff(SQLiteDatabase db) {
        ArrayList<Staff> staffList = new ArrayList<>();
        String[] columns = {COLUMN_ID, COLUMN_FIRST_NAME, COLUMN_LAST_NAME, COLUMN_PHONE_NUMBER, COLUMN_ROLE, COLUMN_GENDER, COLUMN_AVATAR, COLUMN_ACCOUNT_ID};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String firstName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME));
            String lastName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_NAME));
            String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE_NUMBER));
            int role = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ROLE));
            String gender = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER));
            String avatar = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AVATAR));
            int accountID = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ACCOUNT_ID));
            Staff staff = new Staff(id, firstName, lastName, phoneNumber, role, gender, avatar, accountID);
            staffList.add(staff);
        }
        cursor.close();
        return staffList;
    }
    public static Staff getLatestStaff(SQLiteDatabase db) {
        String query = "SELECT * FROM " + TABLE_NAME +
                " ORDER BY " + COLUMN_ID + " DESC LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);
        Staff staff = null;
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String firstName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME));
            String lastName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_NAME));
            String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE_NUMBER));
            int role = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ROLE));
            String gender = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER));
            String avatar = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AVATAR));
            int accountID = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ACCOUNT_ID));
            staff = new Staff(id, firstName, lastName, phoneNumber, role, gender, avatar, accountID);
        }
        cursor.close();
        return staff;
    }
    public static ArrayList<Staff> searchStaffByName(String name, SQLiteDatabase db) {
        ArrayList<Staff> staffList = new ArrayList<>();
        String[] columns = {COLUMN_ID, COLUMN_FIRST_NAME, COLUMN_LAST_NAME, COLUMN_PHONE_NUMBER, COLUMN_ROLE, COLUMN_GENDER, COLUMN_AVATAR, COLUMN_ACCOUNT_ID};
        String selection = COLUMN_FIRST_NAME + " LIKE ? OR " + COLUMN_LAST_NAME + " LIKE ?";
        String[] selectionArgs = {"%" + name + "%", "%" + name + "%"};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String firstName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME));
            String lastName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_NAME));
            String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE_NUMBER));
            int role = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ROLE));
            String gender = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER));
            String avatar = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AVATAR));
            int accountID = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ACCOUNT_ID));
            Staff staff = new Staff(id, firstName, lastName, phoneNumber, role, gender, avatar, accountID);
            staffList.add(staff);
        }
        cursor.close();
        return staffList;
    }


}
