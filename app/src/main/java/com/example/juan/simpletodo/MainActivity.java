package com.example.juan.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String ITEM_ADDED = "Item added to List";
    private static final String NO_ITEM = "Must enter text before adding";
    private static final String LOG_SETUP = "Setting up listview listener";
    private static final String LOG_ITEM_REMOVED = "Item removed from list: ";
    private static final String FILENAME = "todo.txt";
    private static final String READ_ERR = "Error reading file";
    private static final String WRITE_ERR = "Error writing file";
    private static final String UPDATE_SUCCESSFUL = "Item updated successfully";
    protected static final int EDIT_REQUEST_CODE = 20; // request code to launch new Activity
    protected static final String ITEM_TEXT = "ItemText";
    protected static final String ITEM_POSITION = "ItemPosition";


    private List<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvItems = findViewById(R.id.lv_item);
        lvItems.setAdapter(itemsAdapter);

        setupListViewListener();
    }

    public void onAddItem(View v) {
        EditText newItem = findViewById(R.id.et_item);
        String text = newItem.getText().toString();
        if ( text.isEmpty() ) {
            Toast.makeText(getApplicationContext(), NO_ITEM, Toast.LENGTH_LONG).show();
            return;
        }
        itemsAdapter.add(text);
        newItem.setText("");
        writeItems();
        Toast.makeText(getApplicationContext(), ITEM_ADDED, Toast.LENGTH_LONG).show();

    }

    private void setupListViewListener() {
        Log.i(getLocalClassName(), LOG_SETUP);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(getLocalClassName(), LOG_ITEM_REMOVED + position);
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);

                intent.putExtra(ITEM_TEXT, items.get(position));
                intent.putExtra(ITEM_POSITION, position);

                startActivityForResult(intent, EDIT_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == EDIT_REQUEST_CODE) {
            String updatedItem = data.getStringExtra(ITEM_TEXT);
            int position = data.getExtras().getInt(ITEM_POSITION);
            items.set(position, updatedItem);
            itemsAdapter.notifyDataSetChanged();
            writeItems();
            Toast.makeText(this, UPDATE_SUCCESSFUL, Toast.LENGTH_SHORT).show();
        }
    }

    private File getDataFile() {
        return new File(getFilesDir(), FILENAME);
    }

    private void readItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        }
        catch (IOException e) {
            Log.e(getLocalClassName(), READ_ERR, e);
            items = new ArrayList<>();
        }

    }

    private void writeItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        }
        catch (IOException e) {
            Log.e(getLocalClassName(), WRITE_ERR, e);

        }
    }
}
