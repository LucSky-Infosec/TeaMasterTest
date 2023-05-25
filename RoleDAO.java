package com.example.teamaster.DB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.teamaster.Models.Role;
import com.example.teamaster.Models.Size;

import java.util.ArrayList;

public class RoleDAO {
    // Constants for table name and column names
    public static final String TABLE_NAME = "Role";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ROLE_NAME = "role_name";

    // Method to create the table for this entity
    public static String createTable() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_ROLE_NAME + " TEXT NOT NULL" +
                ");";
    }

    // Method to add a new role to the database
    public static void addRole(Role role, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, role.getId());
        values.put(COLUMN_ROLE_NAME, role.getRoleName());
        db.insert(TABLE_NAME, null, values);
    }

    // Method to delete a role from the database
    public static void deleteRole(int id, SQLiteDatabase db) {
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    // Method to update a role in the database
    public static void updateRole(Role role, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROLE_NAME, role.getRoleName());
        db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(role.getId())});
    }

    // Method to get a role by its ID from the database
    public static Role getRoleById(int id, SQLiteDatabase db) {
        String[] columns = {COLUMN_ID, COLUMN_ROLE_NAME};
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        Role role = null;
        if (cursor.moveToFirst()) {
            String roleName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROLE_NAME));
            role = new Role(id, roleName);
        }
        cursor.close();
        return role;
    }

    // Method to get all roles from the database
    public static ArrayList<Role> getAllRoles(SQLiteDatabase db) {
        ArrayList<Role> roles = new ArrayList<>();
        String[] columns = {COLUMN_ID, COLUMN_ROLE_NAME};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String roleName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROLE_NAME));
            roles.add(new Role(id, roleName));
        }
        cursor.close();
        return roles;
    }
    public static void createDemoRole(SQLiteDatabase db) {
        Role staffRole = new Role(1,"Nhân viên");
        RoleDAO.addRole(staffRole, db);
        Role adminRole = new Role(2,"Quản lý");
        RoleDAO.addRole(adminRole, db);
    }
}
