package com.example.teamaster.DB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;

import com.example.teamaster.Models.Customer;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class CustomerDAO {
    // Constants for table name and column names
    public static final String TABLE_NAME = "Customers";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_PHONE_NUMBER = "phone_number";
    public static final String COLUMN_BIRTHDAY = "birthday";

    // Method to create the table for this entity
    public static String createTable() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LAST_NAME + " TEXT NOT NULL, " +
                COLUMN_FIRST_NAME + " TEXT NOT NULL, " +
                COLUMN_PHONE_NUMBER + " TEXT NOT NULL, " +
                COLUMN_BIRTHDAY + " DATE NOT NULL, " +
                ");";
    }
    public static android.icu.text.SimpleDateFormat dateFormat = new android.icu.text.SimpleDateFormat("yyyy-MM-dd");


    // Method to add a new customer to the database
    public static void addCustomer(Customer customer, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_LAST_NAME, customer.getLastName());
        values.put(COLUMN_FIRST_NAME, customer.getFirstName());
        values.put(COLUMN_PHONE_NUMBER, customer.getPhoneNumber());
        values.put(COLUMN_BIRTHDAY, customer.getBirthday().toString());
        db.insert(TABLE_NAME, null, values);
    }

    // Method to delete a customer from the database
    public static void deleteCustomer(int id, SQLiteDatabase db) {
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    // Method to update a customer in the database
    public static void updateCustomer(Customer customer, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_LAST_NAME, customer.getLastName());
        values.put(COLUMN_FIRST_NAME, customer.getFirstName());
        values.put(COLUMN_PHONE_NUMBER, customer.getPhoneNumber());
        values.put(COLUMN_BIRTHDAY, customer.getBirthday().toString());
        db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(customer.getId())});
    }

    public static boolean isCustomerExist(int customerId, SQLiteDatabase db) {
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(customerId)});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    // Method to create a demo customer
    public static void createDemoCustomer(SQLiteDatabase db) throws ParseException {
//        Date date = dateFormat.parse(dateString);
        Customer customerDemo1 = new Customer("Nguyễn Văn", "A", "0123456789", dateFormat.parse("2001-09-09"));
        CustomerDAO.addCustomer(customerDemo1, db);
        Customer customerDemo2 = new Customer("Trần Thị", "B", "0123456789",  dateFormat.parse("2002-01-01"));
        CustomerDAO.addCustomer(customerDemo2, db);
        Customer customerDemo3 = new Customer("Lê Văn", "C", "0123456789",  dateFormat.parse("2005-02-02"));
        CustomerDAO.addCustomer(customerDemo3, db);
    }
    @SuppressLint("Range")
    public static Customer getCustomerById(int id, SQLiteDatabase db) {
        String[] columns = {COLUMN_ID, COLUMN_LAST_NAME, COLUMN_FIRST_NAME, COLUMN_PHONE_NUMBER, COLUMN_BIRTHDAY};
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Customer customer = new Customer();
            customer.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            customer.setLastName(cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME)));
            customer.setFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME)));
            customer.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER)));
            customer.setBirthday(new Date(cursor.getString(cursor.getColumnIndex(COLUMN_BIRTHDAY))));
            cursor.close();
            return customer;
        }
        return null;
    }
    @SuppressLint("Range")
    public static ArrayList<Customer> getAllCustomers(SQLiteDatabase db) {
        ArrayList<Customer> customers = new ArrayList<>();
        String[] columns = {COLUMN_ID, COLUMN_LAST_NAME, COLUMN_FIRST_NAME, COLUMN_PHONE_NUMBER, COLUMN_BIRTHDAY};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Customer customer = new Customer();
                customer.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                customer.setLastName(cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME)));
                customer.setFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME)));
                customer.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER)));
                customer.setBirthday(new Date(cursor.getString(cursor.getColumnIndex(COLUMN_BIRTHDAY))));
                customers.add(customer);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return customers;
    }

}
