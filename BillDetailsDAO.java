package com.example.teamaster.DB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.teamaster.Models.BillDetails;
import com.example.teamaster.Models.Table;

import java.text.ParseException;
import java.util.ArrayList;

public class BillDetailsDAO {

    // Constants for table name and column names
    public static final String TABLE_NAME = "BillDetails";
    public static final String COLUMN_BILL_ID = "bill_id";
    public static final String COLUMN_SIZE_ID = "size_id";
    public static final String COLUMN_FOOD_ID = "food_id";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_PRICE = "price";

    // Method to create the table for this entity
    public static String createTable() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_BILL_ID + " INTEGER NOT NULL, " +
                COLUMN_SIZE_ID + " INTEGER NOT NULL, " +
                COLUMN_FOOD_ID + " INTEGER NOT NULL, " +
                COLUMN_AMOUNT + " INTEGER NOT NULL, " +
                COLUMN_PRICE + " REAL NOT NULL, " +
                "FOREIGN KEY(" + COLUMN_BILL_ID + ") REFERENCES " + BillDAO.TABLE_NAME + "(" + BillDAO.COLUMN_ID + ")," +
                "FOREIGN KEY(" + COLUMN_SIZE_ID + ") REFERENCES " + SizeDAO.TABLE_NAME + "(" + SizeDAO.COLUMN_ID + ")," +
                "FOREIGN KEY(" + COLUMN_FOOD_ID + ") REFERENCES " + FoodDAO.TABLE_NAME + "(" + FoodDAO.COLUMN_ID + ")," +
                "PRIMARY KEY(" + COLUMN_BILL_ID + ", " + COLUMN_SIZE_ID + ", " + COLUMN_FOOD_ID + ")" +
                ");";
    }
    // Method to add a new bill detail to the database
    public static void addBillDetail(BillDetails billDetail, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_BILL_ID, billDetail.getBillID());
        values.put(COLUMN_SIZE_ID, billDetail.getSizeID());
        values.put(COLUMN_FOOD_ID, billDetail.getFoodID());
        values.put(COLUMN_AMOUNT, billDetail.getAmount());
        values.put(COLUMN_PRICE, billDetail.getPrice());
        db.insert(TABLE_NAME, null, values);
    }

    // Method to delete a bill detail from the database
    public static void deleteBillDetail(int billID, int sizeID, int foodID, SQLiteDatabase db) {
        String selection = COLUMN_BILL_ID + " = ? AND " + COLUMN_SIZE_ID + " = ? AND " + COLUMN_FOOD_ID + " = ?";
        String[] selectionArgs = {String.valueOf(billID), String.valueOf(sizeID), String.valueOf(foodID)};
        db.delete(TABLE_NAME, selection, selectionArgs);
    }

    // Method to update a bill detail in the database
    public static void updateBillDetail(BillDetails billDetail, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_BILL_ID, billDetail.getBillID());
        values.put(COLUMN_SIZE_ID, billDetail.getSizeID());
        values.put(COLUMN_FOOD_ID, billDetail.getFoodID());
        values.put(COLUMN_AMOUNT, billDetail.getAmount());
        values.put(COLUMN_PRICE, billDetail.getPrice());
        String selection = COLUMN_BILL_ID + " = ? AND " + COLUMN_SIZE_ID + " = ? AND " + COLUMN_FOOD_ID + " = ?";
        String[] selectionArgs = {String.valueOf(billDetail.getBillID()), String.valueOf(billDetail.getSizeID()), String.valueOf(billDetail.getFoodID())};
        db.update(TABLE_NAME, values, selection, selectionArgs);
    }
    // get a list of all bill details from the database
    @SuppressLint("Range")
    public static ArrayList<BillDetails> getAllBillDetails(SQLiteDatabase db) {
        ArrayList<BillDetails> billDetailsList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int billID = cursor.getInt(cursor.getColumnIndex(COLUMN_BILL_ID));
                int sizeID = cursor.getInt(cursor.getColumnIndex(COLUMN_SIZE_ID));
                int foodID = cursor.getInt(cursor.getColumnIndex(COLUMN_FOOD_ID));
                int amount = cursor.getInt(cursor.getColumnIndex(COLUMN_AMOUNT));
                double price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE));
                BillDetails billDetails = new BillDetails(billID, sizeID, foodID, amount, price);
                billDetailsList.add(billDetails);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return billDetailsList;
    }
    // Method to retrieve all bill details for a specific bill from the database
    @SuppressLint("Range")
    public static ArrayList<BillDetails> getBillDetailsByBillId(int billId, SQLiteDatabase db) {
        ArrayList<BillDetails> billDetailsList = new ArrayList<>();
        String[] columns = {COLUMN_BILL_ID, COLUMN_SIZE_ID, COLUMN_FOOD_ID, COLUMN_AMOUNT, COLUMN_PRICE};
        String selection = COLUMN_BILL_ID + "=?";
        String[] selectionArgs = {String.valueOf(billId)};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int sizeID = cursor.getInt(cursor.getColumnIndex(COLUMN_SIZE_ID));
                int foodID = cursor.getInt(cursor.getColumnIndex(COLUMN_FOOD_ID));
                int amount = cursor.getInt(cursor.getColumnIndex(COLUMN_AMOUNT));
                double price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE));
                BillDetails billDetails = new BillDetails(billId, sizeID, foodID, amount, price);
                billDetailsList.add(billDetails);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return billDetailsList;
    }
    // Method get total price from id bill
    @SuppressLint("Range")
    public static double getTotalMoneyFromBillId(int billId, SQLiteDatabase db) {
        double totalAmount = 0;
        String[] columns = {COLUMN_PRICE, COLUMN_AMOUNT};
        String selection = COLUMN_BILL_ID + "=?";
        String[] selectionArgs = {String.valueOf(billId)};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                double price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE));
                int amount = cursor.getInt(cursor.getColumnIndex(COLUMN_AMOUNT));
                totalAmount += price * amount;
            } while (cursor.moveToNext());
        }
        cursor.close();
        return totalAmount;
    }

    // Method to create some demo tables
    public static void createDemoBillDetails(SQLiteDatabase db) throws ParseException {
        //Bill 1
        double price1 = FoodDetailsDAO.getFoodDetails(1,1, db).getPrice();
        BillDetails DemoBillDetails1 = new BillDetails(1,1,1,2,price1);
        //Bill 2
        double price2 = FoodDetailsDAO.getFoodDetails(3,2, db).getPrice();
        BillDetails DemoBillDetails2 = new BillDetails(2,3,2,1,price2);
        //Bill 3
        double price3 = FoodDetailsDAO.getFoodDetails(1,1, db).getPrice();
        BillDetails DemoBillDetails3 = new BillDetails(3, 1,1,1,price3);
        double price4 = FoodDetailsDAO.getFoodDetails(1,1, db).getPrice();
        BillDetails DemoBillDetails4 = new BillDetails(3, 4,1,1,price4);
    }
}
