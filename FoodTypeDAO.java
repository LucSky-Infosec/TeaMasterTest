package com.example.teamaster.DB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.teamaster.Models.FoodType;

import java.util.ArrayList;

public class FoodTypeDAO {
    // Constants for table name and column names
    public static final String TABLE_NAME = "FoodTypes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TYPE_NAME = "type_name";

    // Method to create the table for this entity
    public static String createTable() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TYPE_NAME + " TEXT NOT NULL);";
    }

    // Method to add a new food type to the database
    public static void addFoodType(FoodType foodType, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TYPE_NAME, foodType.getTypeName());
        db.insert(TABLE_NAME, null, values);
    }

    // Method to delete a food type from the database
    public static void deleteFoodType(int id, SQLiteDatabase db) {
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    // Method to update a food type in the database
    public static void updateFoodType(FoodType foodType, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TYPE_NAME, foodType.getTypeName());
        db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(foodType.getId())});
    }

    // Method to get all food types from the database
    public static ArrayList<FoodType> getAllFoodTypes(SQLiteDatabase db) {
        ArrayList<FoodType> foodTypes = new ArrayList<>();
        String[] columns = {COLUMN_ID, COLUMN_TYPE_NAME};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String typeName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE_NAME));
            foodTypes.add(new FoodType(id, typeName));
        }
        cursor.close();
        return foodTypes;
    }

    // Method to check if a food type exists in the database
    public static boolean isFoodTypeExist(String foodTypeId, SQLiteDatabase db) {
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{foodTypeId});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    // Method to create demo food types
    public static void createDemoFoodTypes(SQLiteDatabase db) {
        FoodType foodType1 = new FoodType("Trà sữa");
        addFoodType(foodType1, db);
        FoodType foodType2 = new FoodType("Cà phê");
        addFoodType(foodType2, db);
        FoodType foodType3 = new FoodType("Bánh ngọt");
        addFoodType(foodType3, db);
    }

    // Method to get a food type by its ID
    public static FoodType getFoodTypeById(int id, SQLiteDatabase db) {
        String[] columns = {COLUMN_ID, COLUMN_TYPE_NAME};
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        FoodType foodType = null;
        if (cursor.moveToFirst()) {
            String typeName =cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE_NAME));
            foodType = new FoodType(id, typeName);
        }
        cursor.close();
        return foodType;
    }
}
