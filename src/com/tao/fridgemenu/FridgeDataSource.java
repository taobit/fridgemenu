package com.tao.fridgemenu;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class FridgeDataSource {
	// Database fields
	private SQLiteDatabase database;
	private FridgeSQLiteHelper helper;
	private String[] allColumns = { FridgeSQLiteHelper.COLUMN_ID,
			FridgeSQLiteHelper.COLUMN_FOOD };

	public FridgeDataSource(Context context) {
		helper = new FridgeSQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = helper.getWritableDatabase();
	}

	public void close() {
		helper.close();
	}

	public Food createFood(String food) {
		ContentValues values = new ContentValues();
		values.put(FridgeSQLiteHelper.COLUMN_FOOD, food);
		long insertId = database.insert(FridgeSQLiteHelper.TABLE_FRIDGE, null,
				values);
		Cursor cursor = database.query(FridgeSQLiteHelper.TABLE_FRIDGE,
				allColumns, FridgeSQLiteHelper.COLUMN_ID + " = " + insertId,
				null, null, null, null);
		cursor.moveToFirst();
		Food newFood = cursortoFood(cursor);
		cursor.close();
		return newFood;
	}

	public void deleteFood(Food food) {
		long id = food.getId();
		System.out.println("food deleted with id: " + id);
		database.delete(FridgeSQLiteHelper.TABLE_FRIDGE,
				FridgeSQLiteHelper.COLUMN_ID + " = " + id, null);
	}

	public List<Food> getAllFood() {
		List<Food> foods = new ArrayList<Food>();

		Cursor cursor = database.query(FridgeSQLiteHelper.TABLE_FRIDGE,
				allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Food food = cursortoFood(cursor);
			foods.add(food);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return foods;
	}

	private Food cursortoFood(Cursor cursor) {
		Food food = new Food();
		food.setId(cursor.getLong(0));
		food.setFood(cursor.getString(1));
		return food;
	}
}
