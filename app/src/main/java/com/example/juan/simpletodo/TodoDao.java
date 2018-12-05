package com.example.juan.simpletodo;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TodoDao {
    @Query("SELECT * FROM Todo")
    List<Todo> getAll();

    @Insert
    void insertAll(Todo... items);

    @Delete
    void delete(Todo todo);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Todo todo);


}
