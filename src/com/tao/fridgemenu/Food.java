package com.tao.fridgemenu;

public class Food {
	private long id;
	private String food;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFood() {
		return food;
	}

	public void setFood(String food) {
		this.food = food;
	}

	// Used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return food;
	}
}
