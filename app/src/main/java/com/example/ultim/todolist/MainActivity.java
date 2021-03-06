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
    private FileManager fileManager;

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
                fileManager.SaveTodoArray(arrayListTodo);
                return true;
            }
            // add stuff here
            case R.id.action_change:{
                Intent myIntent = new Intent(getBaseContext(), AddTodoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("object", arrayListTodo.get(info.position));
                myIntent.putExtras(bundle);
                startActivityForResult(myIntent, 1);
                adapterTodo.notifyDataSetChanged();
                return true;
            }
            case R.id.action_done:{
                if (!arrayListTodo.get(info.position).isDone){
                    arrayListTodo.get(info.position).isDone = true;
                }else {
                    arrayListTodo.get(info.position).isDone = false;
                }
                fileManager.SaveTodoArray(arrayListTodo);
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
        fileManager.SaveTodoArray(arrayListTodo);
        adapterTodo.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fileManager = new FileManager(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getBaseContext(), AddTodoActivity.class);
                startActivityForResult(myIntent, 1);
            }
        });
        arrayListTodo = new ArrayList<>();
        arrayListTodo = fileManager.ReadTodoArray();
        adapterTodo = new AdapterTodo(this, arrayListTodo);

        lv = (ListView) findViewById(R.id.todo_list);
        registerForContextMenu(lv);
        lv.setItemsCanFocus(true);
        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(getBaseContext(), PreviewTodoActivity.class);
                myIntent.putExtra("position", position);
                startActivity(myIntent);
            }

        });

        lv.setAdapter(adapterTodo);
    }

}
