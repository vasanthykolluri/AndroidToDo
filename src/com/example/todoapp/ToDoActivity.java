package com.example.todoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ToDoActivity extends Activity {
	private ArrayList<String> toDoItems;
	private ArrayAdapter<String> toDoAdapter;
	private ListView ToDoList;
	private EditText etNewItem;
	private final int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        etNewItem = (EditText) findViewById(R.id.NewItemText);
       ToDoList = (ListView) findViewById(R.id.ToDoList);
       readItems();
       toDoAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, toDoItems);
       ToDoList.setAdapter(toDoAdapter);
       setupListViewListener();
    }


    private void setupListViewListener() {
    	ToDoList.setOnItemLongClickListener(new OnItemLongClickListener() {
    		@Override
    		public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
    			toDoItems.remove(pos);
    			toDoAdapter.notifyDataSetChanged();
    			writeItems();
    			return true;
    		}
    	});
    	
    	ToDoList.setOnItemClickListener(new OnItemClickListener() {
    		public void onItemClick(AdapterView<?> parent, View view, int position, long id){

                Intent intent = new Intent(getApplicationContext(), EditItemActivity.class);
                String itemText = toDoAdapter.getItem(position);
                intent.putExtra("position", position);
                intent.putExtra("itemText", itemText);
                startActivityForResult(intent, REQUEST_CODE);    		}
    	});
	}


    public void onAddedItem(View v) {
    	String itemText = etNewItem.getText().toString();
    	toDoAdapter.add(itemText);
    	etNewItem.setText("");
    	writeItems();
    }
    
    private void readItems() {
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try {
    		toDoItems = new ArrayList<String>(FileUtils.readLines(todoFile));
    	} catch (IOException e) {
    		toDoItems = new ArrayList<String>();
    	}
    }
    
    private void writeItems() {
    	File filesDir = getFilesDir();
    	File todoFile = new File(filesDir, "todo.txt");
    	try {
    		FileUtils.writeLines(todoFile,  toDoItems);    	
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.to_do, menu);
        return true;
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  // REQUEST_CODE is defined above
	  if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
	     String editItemText = data.getExtras().getString("editItemText");
	     int position = data.getExtras().getInt("position");
	     
	     toDoItems.set(position, editItemText);
		 toDoAdapter.notifyDataSetChanged();
		 writeItems();
	  }
	} 

}
