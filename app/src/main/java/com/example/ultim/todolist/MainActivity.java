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
import java.util.HashMap;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ArrayList<HashMap<String, String>> listTodo;
    ListView lv;
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
            case R.id.action_done:{
                if (listTodo.get(info.position).get("done").equals("false")){
                    listTodo.get(info.position).remove("done");
                    listTodo.get(info.position).put("done", "true");
                }else {
                    listTodo.get(info.position).remove("done");
                    listTodo.get(info.position).put("done", "false");
                }

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
                Intent myIntent = new Intent(getBaseContext(), AddTodoActivity.class);
                startActivity(myIntent);
                ////Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                      //  .setAction("Action", null).show();
            }
        });


        listTodo = new ArrayList<>(new Adapter().getTodoMap());

       adapter = new SimpleAdapter(getBaseContext(), listTodo,
                R.layout.p_list_item, new String[]{"title", "text"},
                new int[]{R.id.title_todo, R.id.text_todo}) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ImageView img = (ImageView) v.findViewById(R.id.image_comp);
                if (listTodo.get(position).get("done") == "true") {
                    img.setImageResource(R.drawable.ic_action_name_end);
                }else {
                    img.setImageResource(R.drawable.ic_action_name);
                }

                if (listTodo.get(position).get("priority") == "max") {
                    v.findViewById(R.id.priority_texture).setBackgroundColor(getResources().getColor(R.color.colorPriorityHighest));
                }
                if (listTodo.get(position).get("priority") == "medium") {
                    v.findViewById(R.id.priority_texture).setBackgroundColor(getResources().getColor(R.color.colorPriorityMedium));
                }
                if (listTodo.get(position).get("priority") == "low") {
                    v.findViewById(R.id.priority_texture).setBackgroundColor(getResources().getColor(R.color.colorPriorityLow));
                }
                return v;
            }
        };
        lv = (ListView) findViewById(R.id.todo_list);
        registerForContextMenu(lv);


        lv.setAdapter(adapter);
    }
}
