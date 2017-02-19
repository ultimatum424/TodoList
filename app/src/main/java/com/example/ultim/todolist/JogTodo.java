package com.example.ultim.todolist;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Ultim on 16.02.2017.
 */

public class JogTodo implements Serializable {
    public String title;
    public String text;
    public String priority;
    public String date;
    public boolean isDone;


    public JogTodo(String title, String text, String colorPriorityMedium, String date, boolean isDone) {
        this.title = title;
        this.text = text;
        this.priority = colorPriorityMedium;
        this.date = date;
        this.isDone = isDone;
    }
}