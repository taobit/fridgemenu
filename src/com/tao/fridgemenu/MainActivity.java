package com.tao.fridgemenu;

import java.util.List;
import java.util.Random;

import android.os.Bundle;
import android.app.ListActivity;
import android.view.View;
import android.widget.ArrayAdapter;

public class MainActivity extends ListActivity {

	private FridgeDataSource datasource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		datasource = new FridgeDataSource(this);
		datasource.open();
		List<Food> values = datasource.getAllFood();

		// Use SimpleCursorAdapter to display data
		ArrayAdapter<Food> adapter = new ArrayAdapter<Food>(this,
				android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	}

	public void onClick(View view) {
		@SuppressWarnings("unchecked")
		ArrayAdapter<Food> adapter = (ArrayAdapter<Food>) getListAdapter();
		Food food = null;
		switch (view.getId()) {
		case R.id.add:
			String[] names = new String[] { "Rice", "Milk" };
			int nextInt = new Random().nextInt(2);
			// Save the new food to the database
			food = datasource.createFood(names[nextInt]);
			adapter.add(food);
			break;
		case R.id.delete:
			if (getListAdapter().getCount() > 0) {
				food = (Food) getListAdapter().getItem(0);
				datasource.deleteFood(food);
				adapter.remove(food);
			}
			break;
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onResume() {
		datasource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}

}
