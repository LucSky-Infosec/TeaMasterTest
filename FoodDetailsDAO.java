package com.example.teamaster.DB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.teamaster.Models.FoodDetails;

public class FoodDetailsDAO {
    // Constants for table name and column names
    public static final String TABLE_NAME = "FoodDetails";
    public static final String COLUMN_FOOD_ID = "food_id";
    public static final String COLUMN_SIZE_ID = "size_id";
    public static final String COLUMN_PRICE = "price";

    // Method to create the table for this entity
    public static String createTable() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_FOOD_ID + " INTEGER NOT NULL, " +
                COLUMN_SIZE_ID + " INTEGER NOT NULL, " +
                COLUMN_PRICE + " INTEGER NOT NULL, " +
                "FOREIGN KEY(" + COLUMN_FOOD_ID + ") REFERENCES " + FoodDAO.TABLE_NAME + "(" + FoodDAO.COLUMN_ID + ")," +
                "FOREIGN KEY(" + COLUMN_SIZE_ID + ") REFERENCES " + SizeDAO.TABLE_NAME + "(" + SizeDAO.COLUMN_ID + ")," +
                "PRIMARY KEY(" + COLUMN_FOOD_ID + ", " + COLUMN_SIZE_ID + ")" +
                ");";
    }

    // Method to add a new food details to the database
    public static void addFoodDetails(FoodDetails foodDetails, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_FOOD_ID, foodDetails.getFoodID());
        values.put(COLUMN_SIZE_ID, foodDetails.getSizeID());
        values.put(COLUMN_PRICE, foodDetails.getPrice());
        db.insert(TABLE_NAME, null, values);
    }

    // Method to delete a food details from the database
    public static void deleteFoodDetails(int foodID, int sizeID, SQLiteDatabase db) {
        db.delete(TABLE_NAME, COLUMN_FOOD_ID + "=? AND " + COLUMN_SIZE_ID + "=?", new String[]{String.valueOf(foodID), String.valueOf(sizeID)});
    }

    // Method to update a food details in the database
    public static void updateFoodDetails(FoodDetails foodDetails, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRICE, foodDetails.getPrice());
        db.update(TABLE_NAME, values, COLUMN_FOOD_ID + "=? AND " + COLUMN_SIZE_ID + "=?", new String[]{String.valueOf(foodDetails.getFoodID()), String.valueOf(foodDetails.getSizeID())});
    }

    // Method to get all food details from the database
    public static Cursor getAllFoodDetails(SQLiteDatabase db) {
        String[] columns = {COLUMN_FOOD_ID, COLUMN_SIZE_ID, COLUMN_PRICE};
        return db.query(TABLE_NAME, columns, null, null, null, null, null);
    }

    // Method to check if a food details exists in the database
    public static boolean isFoodDetailsExist(int foodID, int sizeID, SQLiteDatabase db) {
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_FOOD_ID + " = ? AND " + COLUMN_SIZE_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(foodID), String.valueOf(sizeID)});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    // Method to get all food details for a specific food from the database
    public static Cursor getAllFoodDetailsForFood(int foodID, SQLiteDatabase db) {
        String[] columns = {COLUMN_FOOD_ID, COLUMN_SIZE_ID, COLUMN_PRICE};
        String selection = COLUMN_FOOD_ID + "=?";
        String[] selectionArgs = {String.valueOf(foodID)};
        return db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
    }

    // Method to get a specific food details from the database
    @SuppressLint("Range")
    public static FoodDetails getFoodDetails(int foodID, int sizeID, SQLiteDatabase db) {
        String[] columns = {COLUMN_FOOD_ID, COLUMN_SIZE_ID, COLUMN_PRICE};
        String selection = COLUMN_FOOD_ID + "=? AND " + COLUMN_SIZE_ID + "=?";
        String[] selectionArgs = {String.valueOf(foodID), String.valueOf(sizeID)};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        FoodDetails foodDetails = new FoodDetails();
        foodDetails.setFoodID(cursor.getInt(cursor.getColumnIndex(COLUMN_FOOD_ID)));
        foodDetails.setSizeID(cursor.getInt(cursor.getColumnIndex(COLUMN_SIZE_ID)));
        foodDetails.setPrice(cursor.getInt(cursor.getColumnIndex(COLUMN_PRICE)));
        cursor.close();
        return foodDetails;
    }

    // Method to create demo food details
    public static void createDemoFoodDetails(SQLiteDatabase db) {
        FoodDetails foodDetailsDemo1 = new FoodDetails(1, 1, 20000);
        FoodDetails foodDetailsDemo2 = new FoodDetails(1, 2, 30000);
        FoodDetails foodDetailsDemo3 = new FoodDetails(1, 2, 35000);
        FoodDetails foodDetailsDemo4 = new FoodDetails(2, 1, 20000);
        FoodDetails foodDetailsDemo5 = new FoodDetails(2, 2, 30000);
        FoodDetails foodDetailsDemo6 = new FoodDetails(2, 3, 35000);
        FoodDetails foodDetailsDemo7 = new FoodDetails(3, 1, 20000);
        FoodDetails foodDetailsDemo8 = new FoodDetails(3, 2, 30000);
        FoodDetails foodDetailsDemo9 = new FoodDetails(4, 1, 25000);
        FoodDetails foodDetailsDemo10 = new FoodDetails(4, 2, 35000);
        FoodDetailsDAO.addFoodDetails(foodDetailsDemo1, db);
        FoodDetailsDAO.addFoodDetails(foodDetailsDemo2, db);
        FoodDetailsDAO.addFoodDetails(foodDetailsDemo3, db);
        FoodDetailsDAO.addFoodDetails(foodDetailsDemo4, db);
        FoodDetailsDAO.addFoodDetails(foodDetailsDemo5, db);
        FoodDetailsDAO.addFoodDetails(foodDetailsDemo6, db);
        FoodDetailsDAO.addFoodDetails(foodDetailsDemo7, db);
        FoodDetailsDAO.addFoodDetails(foodDetailsDemo8, db);
        FoodDetailsDAO.addFoodDetails(foodDetailsDemo9, db);
        FoodDetailsDAO.addFoodDetails(foodDetailsDemo10, db);
    }
}