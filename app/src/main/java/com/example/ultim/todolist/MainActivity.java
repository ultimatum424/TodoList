package com.example.ultim.todolist;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ArrayList<HashMap<String, String>> listTodo;
    ///private SelectionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        listTodo = new ArrayList<>(new Adapter().getTodoMap());

        ListAdapter adapter = new SimpleAdapter(getBaseContext(), listTodo,
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
        final ListView lv = (ListView) findViewById(R.id.todo_list);

        lv.setAdapter(adapter);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (!view.isSelected()){
                    view.setSelected(true);
                    view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }else {
                    view.setSelected(false);
                    view.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                }


                return true;

            }
        });
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
           // mActionMode = null;

        }
    };
}
