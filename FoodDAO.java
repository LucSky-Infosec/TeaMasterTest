package com.example.teamaster.DB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.teamaster.Models.Food;
import com.example.teamaster.Models.Staff;

import java.util.ArrayList;

public class FoodDAO {

    // Constants for table name and column names
    public static final String TABLE_NAME = "Food";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_food_NAME = "food_name";
    public static final String COLUMN_UNIT = "unit";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_TYPE = "type";

    // Method to create the table for this entity
    public static String createTable() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_food_NAME + " TEXT NOT NULL, " +
                COLUMN_UNIT + " TEXT NOT NULL, " +
                COLUMN_IMAGE + " TEXT NOT NULL, " +
                COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                COLUMN_TYPE + " INTEGER NOT NULL " +
                ");";
    }
    // Method to add a new food item to the database
    public static void addFoodItem(Food FoodItem, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_food_NAME, FoodItem.getFoodName());
        values.put(COLUMN_UNIT, FoodItem.getUnit());
        values.put(COLUMN_IMAGE, FoodItem.getImage());
        values.put(COLUMN_DESCRIPTION, FoodItem.getDescription());
        values.put(COLUMN_TYPE, FoodItem.getType());
        db.insert(TABLE_NAME, null, values);
    }

    // Method to delete a Food item from the database
    public static void deleteFoodItem(int id, SQLiteDatabase db) {
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    // Method to update a Food item in the database
    public static void updateFoodItem(Food FoodItem, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_food_NAME, FoodItem.getFoodName());
        values.put(COLUMN_UNIT, FoodItem.getUnit());
        values.put(COLUMN_IMAGE, FoodItem.getImage());
        values.put(COLUMN_DESCRIPTION, FoodItem.getDescription());
        values.put(COLUMN_TYPE, FoodItem.getType());
        db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(FoodItem.getId())});
    }

    // Method to get a list of all Food items in the database
    public static ArrayList<Food> getAllFoodItems(SQLiteDatabase db) {
        ArrayList<Food> FoodItems = new ArrayList<>();
        String[] columns = {COLUMN_ID, COLUMN_food_NAME, COLUMN_UNIT, COLUMN_IMAGE, COLUMN_DESCRIPTION, COLUMN_TYPE};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String foodName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_food_NAME));
            String unit = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UNIT));
            String image = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
            int type = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TYPE));
            Food FoodItem = new Food(id, foodName, unit, image, description, type);
            FoodItems.add(FoodItem);
        }
        cursor.close();
        return FoodItems;
    }

    // Method to get a Food item by ID
    public static Food getFoodItemById(int id, SQLiteDatabase db) {
        String[] columns = {COLUMN_ID, COLUMN_food_NAME, COLUMN_UNIT, COLUMN_IMAGE, COLUMN_DESCRIPTION, COLUMN_TYPE};
        String selection = COLUMN_ID + "=?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        Food FoodItem = null;
        if (cursor.moveToFirst()) {
            String foodName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_food_NAME));
            String unit = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_UNIT));
            String image = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
            int type = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TYPE));
            FoodItem = new Food(id, foodName, unit, image, description, type);
        }
        cursor.close();
        return FoodItem;
    }
    public static void createDemoFood(SQLiteDatabase db) {
        Food food1 = new Food("Trà sữa trân châu","ly","@drawable.milk_tea1.jpg","1 loại trà sữa truyền thống",1);
        Food food2 = new Food("Trà sữa Macchiato","ly","@drawable.milk_tea1.jpg","kết hợp giữa hương vị phương đông và tây, có lớp kem Macchiato",1);
        Food food3 = new Food("Cà phê sữa nóng","ly","@drawable.coffee1.jpg",null,1);
        Food food4 = new Food("Hamburger","phần","@drawable.hamburger.jpg","Sẩn phẩm bánh ngọt đang hot",1);
        FoodDAO.addFoodItem(food1,db);
        FoodDAO.addFoodItem(food2,db);
        FoodDAO.addFoodItem(food3,db);
        FoodDAO.addFoodItem(food4,db);
    }
}
