package com.example.teamaster.DB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.teamaster.Models.Promotion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PromotionDAO {
    // Constants for table name and column names
    public static final String TABLE_NAME = "Promotion";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_START_DATE = "start_date";
    public static final String COLUMN_END_DATE = "end_date";
    public static final String COLUMN_VALUE = "value";
    public static final String COLUMN_DESCRIPTION = "description";

    // Method to create the table for this entity
    public static String createTable() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_START_DATE + " DATE NOT NULL, " +
                COLUMN_END_DATE + " DATE NOT NULL, " +
                COLUMN_VALUE + " INTEGER NOT NULL, " +
                COLUMN_DESCRIPTION + " TEXT NOT NULL" +
                ");";
    }
    public static android.icu.text.SimpleDateFormat dateFormat = new android.icu.text.SimpleDateFormat("yyyy-MM-dd");

    // Method to add a new promotion to the database
    public static void addPromotion(Promotion promotion, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_START_DATE, dateFormat.format(promotion.getStartDate()));
        values.put(COLUMN_END_DATE, dateFormat.format(promotion.getEndDate()));
        values.put(COLUMN_VALUE, promotion.getValue());
        values.put(COLUMN_DESCRIPTION, promotion.getDescription());
        db.insert(TABLE_NAME, null, values);
    }

    // Method to delete a promotion from the database
    public static void deletePromotion(int id, SQLiteDatabase db) {
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    // Method to update a promotion in the database
    public static void updatePromotion(Promotion promotion, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_START_DATE, dateFormat.format(promotion.getStartDate()));
        values.put(COLUMN_END_DATE, dateFormat.format(promotion.getEndDate()));
        values.put(COLUMN_VALUE, promotion.getValue());
        values.put(COLUMN_DESCRIPTION, promotion.getDescription());
        db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(promotion.getId())});
    }

    // Method to get all promotions from the database
    public static ArrayList<Promotion> getAllPromotions(SQLiteDatabase db) {
        ArrayList<Promotion> promotions = new ArrayList<>();
        String[] columns = {COLUMN_ID, COLUMN_START_DATE, COLUMN_END_DATE, COLUMN_VALUE, COLUMN_DESCRIPTION};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String startDateString = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_START_DATE));
            String endDateString = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_END_DATE));
            int value = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_VALUE));
            String reason = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = null;
            Date endDate = null;
            try {
                startDate = dateFormat.parse(startDateString);
                endDate = dateFormat.parse(endDateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Promotion promotion = new Promotion(id, startDate, endDate, value, reason);
            promotions.add(promotion);
        }
        cursor.close();
        return promotions;
    }

    // Method to check if a promotion exists in the database
    public static boolean isPromotionExist(int promotionId, SQLiteDatabase db) {
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(promotionId)});
        return false;
    }

    // Method to get a specific promotion from the database
    public static Promotion getPromotionById(int id, SQLiteDatabase db) {
        String[] columns = {COLUMN_ID, COLUMN_START_DATE, COLUMN_END_DATE, COLUMN_VALUE, COLUMN_DESCRIPTION};
        String selection = COLUMN_ID + "=?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        Promotion promotion = null;
        if (cursor.moveToFirst()) {
            String startDateString = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_START_DATE));
            String endDateString = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_END_DATE));
            int value = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_VALUE));
            String reason = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = null;
            Date endDate = null;
            try {
                startDate = dateFormat.parse(startDateString);
                endDate = dateFormat.parse(endDateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            promotion = new Promotion(id, startDate, endDate, value, reason);
        }
        cursor.close();
        return promotion;
    }

    public void createDemoData(SQLiteDatabase db) throws ParseException {
        Date startDate1 = dateFormat.parse("2023-05-01");
        Date endDate1 = dateFormat.parse("2023-05-31");
        Promotion promotion1 = new Promotion(startDate1, endDate1, 10, "Summer Sale");
        PromotionDAO.addPromotion(promotion1, db);

        Date startDate2 = dateFormat.parse("2023-06-01");
        Date endDate2 = dateFormat.parse("2023-06-30");
        Promotion promotion2 = new Promotion(startDate2, endDate2, 15, "Mid-Year Promotion");
        PromotionDAO.addPromotion(promotion2, db);
    }
}
