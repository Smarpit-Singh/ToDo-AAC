package com.example.android.todoaac.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task ORDER BY priority")
    List<TaskEntry> loadAllTasks();

    @Insert
    void insertTask(TaskEntry task);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(TaskEntry task);

    @Delete
    void deleteTask(TaskEntry task);
}
