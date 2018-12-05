package com.example.juan.simpletodo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Todo {

    Todo(String itemText) {
        this.itemText = itemText;
    }

    @ColumnInfo
    @PrimaryKey(autoGenerate = true)
    Long id;

    @ColumnInfo(name = "itemText")
    String itemText;

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }


}
