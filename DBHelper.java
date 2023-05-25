
package com.example.teamaster.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.teamaster.Models.Account;
import com.example.teamaster.Models.Role;
import com.example.teamaster.Models.Staff;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    // Constants for database name and version
    private static final String DATABASE_NAME = "MilkTeaStoreDB";
    private static final int DATABASE_VERSION = 1;

    // Constructor
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables
        db.execSQL(AccountDAO.createTable());
        db.execSQL(StaffDAO.createTable());
        db.execSQL(SizeDAO.createTable());
        db.execSQL(RoleDAO.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade database
        // Drop the old tables
        db.execSQL("DROP TABLE IF EXISTS " + CustomerDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + StaffDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + AccountDAO.TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + OrderDAO.TABLE_NAME);
//        db.execSQL("DROP TABLE IF EXISTS " + OrderItemDAO.TABLE_NAME);
        // Recreate the tables
        onCreate(db);
    }

    //--------------------------------------------Account--------------------------------------------
    // Method to add a new account
    public void addAccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();
        AccountDAO.addAccount(account, db);
        db.close();
    }

    // Method to delete an account
    public void deleteAccount(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        AccountDAO.deleteAccount(id, db);
        db.close();
    }
    
    // Method to update an account
    public void updateAccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();
        AccountDAO.updateAccount(account, db);
        db.close();
    }

    // Method to check exists an account
    public boolean isAccountExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean exists = AccountDAO.isAccountExists(username, db);
        db.close();
        return exists;
    }

    // Method to create a demo account
    public void createDemoAccount() {
        SQLiteDatabase db = this.getWritableDatabase();
        AccountDAO.createDemoAccount(db);
        db.close();
    }
    // Method get Account from username
    public Account getAccountByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Account account = AccountDAO.getAccountByUsername(username,db);
        db.close();
        return account;
    }
    // Method get Account from id
    public Account getAccountById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Account account = AccountDAO.getAccountById(id, db);
        db.close();
        return account;
    }

    //Method get all account
    public ArrayList<Account> getAllAccounts() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Account> accounts = AccountDAO.getAllAccounts(db);
        return accounts;
    }
    // Method to get the latest account
    public Account getLatestAccount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Account account = AccountDAO.getLatestAccount(db);
        db.close();
        return account;
    }


    //--------------------------------------------Staff--------------------------------------------
    // Method to add a new staff
    public void addStaff(Staff staff) {
        SQLiteDatabase db = this.getWritableDatabase();
        StaffDAO.addStaff(staff, db);
        db.close();
    }
    
    // Method to delete a staff
    public void deleteStaff(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        StaffDAO.deleteStaff(id, db);
        db.close();
    }
    
    // Method to update a staff
    public void updateStaff(Staff staff) {
        SQLiteDatabase db = this.getWritableDatabase();
        StaffDAO.updateStaff(staff, db);
        db.close();
    }

    // Method to check exists an staff
    public boolean isStaffExist(String staffId) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean exists = StaffDAO.isStaffExist(staffId, db);
        db.close();
        return exists;
    }

    // Method to create a demo account
    public void createDemoStaff() {
        SQLiteDatabase db = this.getWritableDatabase();
        StaffDAO.createDemoStaff(db);
        db.close();
    }

    // Method get staff from ID
    public Staff getStaffById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Staff staff = StaffDAO.getStaffById(id,db);
        db.close();
        return staff;
    }
    //Method get all staff
    public ArrayList<Staff> getAllStaff() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Staff> staffList = StaffDAO.getAllStaff(db);
        return staffList;
    }
    //Method get latest staff
    public Staff getLatestStaff() {
        SQLiteDatabase db = this.getReadableDatabase();
        Staff latestStaff = StaffDAO.getLatestStaff(db);
        db.close();
        return latestStaff;
    }
    //Method search staff by name

    public ArrayList<Staff> searchStaffByName(String keyword) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Staff> matchingStaffs = StaffDAO.searchStaffByName(keyword, db);
        db.close();
        return matchingStaffs;
    }

    //--------------------------------------------Role--------------------------------------------

    // Method to get a role by its ID from the database
    public Role getRoleById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Role role = RoleDAO.getRoleById(id, db);
        return role;
    }
    public void createDemoRole() {
        SQLiteDatabase db = this.getWritableDatabase();
        RoleDAO.createDemoRole(db);
        db.close();
    }
}
