package com.example.ultim.todolist;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    private ArrayList<JogTodo> arrayListTodo;
    private AdapterTodo adapterTodo;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.todo_list) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_main, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_delete: {
                arrayListTodo.remove(info.position);
                adapterTodo.notifyDataSetChanged();
                return true;
            }
            // add stuff here
            case R.id.action_change:{
                Intent myIntent = new Intent(getBaseContext(), AddTodoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("object", arrayListTodo.get(info.position));
                myIntent.putExtras(bundle);
                startActivityForResult(myIntent, 1);
                //arrayListTodo.add(arrayListTodo.get(info.position));
                adapterTodo.notifyDataSetChanged();
                return true;
            }
            case R.id.action_done:{
                if (!arrayListTodo.get(info.position).isDone){
                    arrayListTodo.get(info.position).isDone = true;
                }else {
                    arrayListTodo.get(info.position).isDone = false;
                }
                adapterTodo.notifyDataSetChanged();
                return true;
            }
            // edit stuff here
            default:
                return super.onContextItemSelected(item);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        Bundle bundle = data.getExtras();
        JogTodo jogTodo = (JogTodo) bundle.getSerializable("object");
        arrayListTodo.add(jogTodo);
        adapterTodo.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getBaseContext(), AddTodoActivity.class);
                startActivityForResult(myIntent, 1);
            }
        });

        arrayListTodo = new ArrayList<>();
        JogTodo jogTodo = new JogTodo("Title", "Text text", "max", "25.02.2016", false);
        JogTodo jogTodo2 = new JogTodo("Title", "Text text", "max", "25.02.2016", false);
        JogTodo jogTodo3 = new JogTodo("Title", "Text text", "max", "25.02.2016", false);
        arrayListTodo.add(jogTodo);
        arrayListTodo.add(jogTodo2);
        arrayListTodo.add(jogTodo3);
        adapterTodo = new AdapterTodo(this, arrayListTodo);

        lv = (ListView) findViewById(R.id.todo_list);
        registerForContextMenu(lv);


        lv.setAdapter(adapterTodo);
    }
}
