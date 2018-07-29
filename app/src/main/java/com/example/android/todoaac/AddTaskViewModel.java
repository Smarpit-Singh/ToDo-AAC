package com.example.android.todoaac;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import com.example.android.todoaac.database.AppDatabase;
import com.example.android.todoaac.database.TaskEntry;

public class AddTaskViewModel extends ViewModel {

    private LiveData<TaskEntry> task;

    public AddTaskViewModel(AppDatabase database, int taskId) {
        task = database.taskDao().loadTaskById(taskId);
    }

    public LiveData<TaskEntry> getTask() {
        return task;
    }
}
