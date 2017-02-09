package com.example.ultim.todolist;

import android.content.Context;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ArrayList<HashMap<String, String>> listTodo;
    ListView lv;
    ///private SelectionAdapter mAdapter;
    private BaseAdapter adapter;

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
                listTodo.remove(info.position);
                adapter.notifyDataSetChanged();
                return true;
            }
            // add stuff here
            case R.id.action_change:{
                listTodo.add(listTodo.get(info.position));
                adapter.notifyDataSetChanged();
                return true;
            }
            // edit stuff here

            default:
                return super.onContextItemSelected(item);
        }
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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        listTodo = new ArrayList<>(new Adapter().getTodoMap());

       adapter = new SimpleAdapter(getBaseContext(), listTodo,
                R.layout.p_list_item, new String[]{"title", "text"},
                new int[]{R.id.title_todo, R.id.text_todo}) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                if (listTodo.get(position).get("priority") == "max") {
                    v.findViewById(R.id.priority_texture).setBackgroundColor(getResources().getColor(R.color.colorAccent));

                }
                return v;
            }
        };
        lv = (ListView) findViewById(R.id.todo_list);
        registerForContextMenu(lv);


        lv.setAdapter(adapter);
    }
}
