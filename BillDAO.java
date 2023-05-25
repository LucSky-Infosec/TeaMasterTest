package com.example.teamaster.DB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.util.ArrayList;

import com.example.teamaster.DB.CustomerDAO;
import com.example.teamaster.DB.PromotionDAO;
import com.example.teamaster.DB.StaffDAO;
import com.example.teamaster.Models.Bill;
import com.example.teamaster.Models.Table;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class BillDAO {

    public static final String TABLE_NAME = "Bills";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PAYMENT_METHOD = "payment_method";
    public static final String COLUMN_CREATED_DATE = "created_date";
    public static final String COLUMN_AMOUNT_OF_MONEY = "amount_of_money";
    public static final String COLUMN_PROMOTION_ID = "promotion_id";
    public static final String COLUMN_STAFF_ID = "staff_id";
    public static final String COLUMN_CUSTOMER_ID = "customer_id";

    // Method to create the table for this entity
    public static String createTable() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PAYMENT_METHOD + " TEXT NOT NULL, " +
                COLUMN_CREATED_DATE + " DATE NOT NULL, " +
                COLUMN_AMOUNT_OF_MONEY + " REAL NOT NULL, " +
                COLUMN_PROMOTION_ID + " INTEGER, " +
                COLUMN_STAFF_ID + " INTEGER NOT NULL, " +
                COLUMN_CUSTOMER_ID + " INTEGER NOT NULL, " +
                "FOREIGN KEY(" + COLUMN_PROMOTION_ID + ") REFERENCES " + PromotionDAO.TABLE_NAME + "(" + PromotionDAO.COLUMN_ID + ")" +
                "FOREIGN KEY(" + COLUMN_STAFF_ID + ") REFERENCES " + StaffDAO.TABLE_NAME + "(" + StaffDAO.COLUMN_ID + ")" +
                "FOREIGN KEY(" + COLUMN_CUSTOMER_ID + ") REFERENCES " + CustomerDAO.TABLE_NAME + "(" + CustomerDAO.COLUMN_ID + ")" +
                ");";
    }
    public static android.icu.text.SimpleDateFormat dateFormat = new android.icu.text.SimpleDateFormat("yyyy-MM-dd");

    // Method to add a new bill to the database
    public static void addBill(Bill bill, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PAYMENT_METHOD, bill.getPaymentMethod());
        values.put(COLUMN_CREATED_DATE, bill.getCreatedDate().getTime());
        values.put(COLUMN_AMOUNT_OF_MONEY, bill.getAmountOfMoney());
        values.put(COLUMN_PROMOTION_ID, bill.getPromotionID());
        values.put(COLUMN_STAFF_ID, bill.getStaffID());
        values.put(COLUMN_CUSTOMER_ID, bill.getCustomerID());
        db.insert(TABLE_NAME, null, values);
    }

    // Method to delete a bill from the database
    public static void deleteBill(int id, SQLiteDatabase db) {
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    // Method to update a bill in the database
    public static void updateBill(Bill bill, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PAYMENT_METHOD, bill.getPaymentMethod());
        values.put(COLUMN_CREATED_DATE, bill.getCreatedDate().getTime());
        values.put(COLUMN_AMOUNT_OF_MONEY, bill.getAmountOfMoney());
        values.put(COLUMN_PROMOTION_ID, bill.getPromotionID());
        values.put(COLUMN_STAFF_ID, bill.getStaffID());
        values.put(COLUMN_CUSTOMER_ID, bill.getCustomerID());
        db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(bill.getId())});
    }

    // Method to retrieve all bills from the database
    @SuppressLint("Range")
    public static ArrayList<Bill> getAllBills(SQLiteDatabase db) {
        ArrayList<Bill> bills = new ArrayList<>();
        String[] columns= {COLUMN_ID, COLUMN_PAYMENT_METHOD, COLUMN_CREATED_DATE, COLUMN_AMOUNT_OF_MONEY, COLUMN_PROMOTION_ID, COLUMN_STAFF_ID, COLUMN_CUSTOMER_ID};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            String paymentMethod = cursor.getString(cursor.getColumnIndex(COLUMN_PAYMENT_METHOD));
            Date createdDate = new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_CREATED_DATE)));
            double amountOfMoney = cursor.getDouble(cursor.getColumnIndex(COLUMN_AMOUNT_OF_MONEY));
            int promotionID = cursor.getInt(cursor.getColumnIndex(COLUMN_PROMOTION_ID));
            int staffID = cursor.getInt(cursor.getColumnIndex(COLUMN_STAFF_ID));
            int customerID = cursor.getInt(cursor.getColumnIndex(COLUMN_CUSTOMER_ID));
            bills.add(new Bill(paymentMethod, createdDate, amountOfMoney, promotionID, staffID, customerID));
        }
        cursor.close();
        return bills;
    }
    // Method to retrieve a bill from the database by id
    @SuppressLint("Range")
    public static Bill getBillById(int id, SQLiteDatabase db) {
        String[] columns= {COLUMN_ID, COLUMN_PAYMENT_METHOD, COLUMN_CREATED_DATE, COLUMN_AMOUNT_OF_MONEY, COLUMN_PROMOTION_ID, COLUMN_STAFF_ID, COLUMN_CUSTOMER_ID};
        String selection = COLUMN_ID + "=?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            String paymentMethod = cursor.getString(cursor.getColumnIndex(COLUMN_PAYMENT_METHOD));
            Date createdDate = new Date(cursor.getLong(cursor.getColumnIndex(COLUMN_CREATED_DATE)));
            double amountOfMoney = cursor.getDouble(cursor.getColumnIndex(COLUMN_AMOUNT_OF_MONEY));
            int promotionID = cursor.getInt(cursor.getColumnIndex(COLUMN_PROMOTION_ID));
            int staffID = cursor.getInt(cursor.getColumnIndex(COLUMN_STAFF_ID));
            int customerID = cursor.getInt(cursor.getColumnIndex(COLUMN_CUSTOMER_ID));
            cursor.close();
            return new Bill(id, paymentMethod, createdDate, amountOfMoney, promotionID, staffID, customerID);
        }
        return null;
    }
    // Method to create demo data for the Bills table
    public static void createDemoData(SQLiteDatabase db) throws ParseException {
        // Create some demo bills
        Double TotalMoney1 = BillDetailsDAO.getTotalMoneyFromBillId(1,db);
        Bill bill1 = new Bill("chuyen khoan", dateFormat.parse("2023-05-01"), TotalMoney1, 0, 1, 1 ,Arrays.asList(1, 2));
        Double TotalMoney2 = BillDetailsDAO.getTotalMoneyFromBillId(2,db);
        Bill bill2 = new Bill("chuyen khoan",  dateFormat.parse("2023-05-02"),  TotalMoney2, 1, 2, 2,Arrays.asList(1));
        Double TotalMoney3 = BillDetailsDAO.getTotalMoneyFromBillId(3,db);
        Bill bill3 = new Bill("tien mat",  dateFormat.parse("2023-05-03"),  TotalMoney3, 2, 3, 3,Arrays.asList(1, 2, 3));
        // Add the demo bills to the database
        addBill(bill1, db);
        addBill(bill2, db);
        addBill(bill3, db);
    }
}
