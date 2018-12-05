package com.example.juan.simpletodo;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

public class TodoDatabase {
    private final AppDatabase db;

    TodoDatabase(Context context) {
        db = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, AppDatabase.NAME).
                allowMainThreadQueries().fallbackToDestructiveMigration().build();
    }

    public List<Todo> getAllItems() {
        return db.todoDao().getAll();
    }

    public void insertItem(Todo item) {
        db.todoDao().insertAll(item);
    }

    public void deleteItem(String itemText) {
        List<Todo> items = db.todoDao().getAll();
        for (Todo item : items) {
            if (item.getItemText().equals(itemText)) {
                db.todoDao().delete(item);
            }
        }
    }

    public void updateItem(Todo item) {
        db.todoDao().update(item);
    }


}
