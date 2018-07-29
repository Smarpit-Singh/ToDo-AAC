package com.example.android.todoaac;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.android.todoaac.database.AppDatabase;
import com.example.android.todoaac.database.TaskEntry;

import java.util.List;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;


public class MainActivity extends AppCompatActivity implements TaskAdapter.ItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private TaskAdapter mAdapter;

    private AppDatabase mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mDB = AppDatabase.getsInstance(getApplicationContext());

        mRecyclerView = findViewById(R.id.recyclerViewTasks);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new TaskAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        mRecyclerView.addItemDecoration(decoration);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Here is where you'll implement swipe to delete
            }
        }).attachToRecyclerView(mRecyclerView);


        FloatingActionButton fabButton = findViewById(R.id.fab);
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent addTaskIntent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(addTaskIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        AppExecutors.getsInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<TaskEntry> taskEntries = mDB.taskDao().loadAllTasks();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setTasks(taskEntries);
                    }
                });
            }
        });
    }

    @Override
    public void onItemClickListener(int itemId) {

    }
}
