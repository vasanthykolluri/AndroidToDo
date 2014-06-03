package com.example.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends Activity {

	private EditText editItem;
	private int position;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
        editItem = (EditText) findViewById(R.id.editText);

		String itemText = getIntent().getStringExtra("itemText");
		position = getIntent().getIntExtra("position", -1);

		editItem.setText(itemText);
		editItem.setSelection(editItem.getText().length());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


    public void onSavedItem(View v) {
    	Intent i = new Intent();
    	String itemText = editItem.getText().toString();
    	i.putExtra("editItemText", itemText);
    	i.putExtra("position", position);
    	setResult(RESULT_OK, i);
    	finish();
    }

}
