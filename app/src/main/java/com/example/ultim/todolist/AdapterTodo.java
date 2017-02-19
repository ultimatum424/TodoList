package com.example.ultim.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Ultim on 16.02.2017.
 */

public class AdapterTodo extends ArrayAdapter<JogTodo> {
    public AdapterTodo(Context context, ArrayList<JogTodo> jog) {
        super(context, 0,  jog);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        JogTodo jogTodo = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.p_list_item, parent, false);
        }
        TextView textTitle = (TextView) convertView.findViewById(R.id.title_todo);
        TextView textText = (TextView) convertView.findViewById(R.id.text_todo);
        View textureView = (View) convertView.findViewById(R.id.priority_texture);
        TextView textDate = (TextView) convertView.findViewById(R.id.text_date);

        textTitle.setText(jogTodo.title);
        textText.setText(jogTodo.text);
        textDate.setText(jogTodo.date);
        if (jogTodo.priority.equals("low")){
            textureView.setBackgroundColor(getContext().getResources().getColor(R.color.colorPriorityLow));
        }
        if (jogTodo.priority.equals("medium")){
            textureView.setBackgroundColor(getContext().getResources().getColor(R.color.colorPriorityMedium));
        }
        if (jogTodo.priority.equals("max")){
            textureView.setBackgroundColor(getContext().getResources().getColor(R.color.colorPriorityHighest));
        }

        return convertView;
    }
}
