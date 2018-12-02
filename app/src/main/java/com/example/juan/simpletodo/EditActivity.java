package com.example.juan.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import static com.example.juan.simpletodo.MainActivity.ITEM_POSITION;
import static com.example.juan.simpletodo.MainActivity.ITEM_TEXT;

public class EditActivity extends AppCompatActivity {

    private static final String EDIT_TITLE = "Edit text";
    EditText editItemText;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        editItemText = findViewById(R.id.et_editText);
        editItemText.setText(getIntent().getStringExtra(ITEM_TEXT));
        position = getIntent().getIntExtra(ITEM_POSITION, 0);
        getSupportActionBar().setTitle(EDIT_TITLE);

    }

    public void onSaveItem(View v) {
        Intent intent = new Intent();
        intent.putExtra(ITEM_TEXT, editItemText.getText().toString());
        intent.putExtra(ITEM_POSITION, position);
        setResult(RESULT_OK, intent);
        finish();
    }
}
