package com.example.sushiltodolist2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.sushiltodolist2.Adapter.ToDoAdapter;
import com.example.sushiltodolist2.Model.ToDoModel;
import com.example.sushiltodolist2.Utils.DatabaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements DialogCloseListener {


    private RecyclerView tasksRecycleView;
    private ToDoAdapter tasksAdapter;
   private List<ToDoModel> taskList;
   private DatabaseHandler db;
   private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null) {
            (getSupportActionBar()).hide();
        }


        tasksRecycleView=findViewById(R.id.tasksRecycleView);
        db=new DatabaseHandler(this);
        db.openDatabase();


        taskList =new ArrayList<>();

        tasksRecycleView.setLayoutManager(new LinearLayoutManager(this));
       tasksAdapter=new ToDoAdapter(db,this);
        tasksRecycleView.setAdapter(tasksAdapter);
        fab=findViewById(R.id.fab);
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new RecyclerItemTouchHelper(tasksAdapter));
itemTouchHelper.attachToRecyclerView(tasksRecycleView);

        //ToDoModel task=new ToDoModel();
        taskList=db.getAllTasks();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(),AddNewTask.TAG);
            }
        });



    }
    @Override
    public void handleDialogClose(DialogInterface dialog){
        taskList=db.getAllTasks();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();
    }

}

