package com.example.ultim.todolist;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by Ultim on 02.03.2017.
 */

public class FileManager {
    private Context context;
    private static final String FILENAME = "js";
    FileManager(Context context) {
        this.context = context;
    }
    public void SaveTodoArray(ArrayList<JogTodo> arrayListTodo){

        JSONArray jsArray = new JSONArray();
        for (JogTodo todo : arrayListTodo){
            JSONObject obj = new JSONObject();
            try {
                obj.put("title", todo.title);
                obj.put("text", todo.text);
                obj.put("priority", todo.priority);
                obj.put("date", todo.date);
                obj.put("isDone", todo.isDone);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsArray.put(obj);
        }

        try {
            String stringToSave = "{\"data\":" + jsArray + "}";
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(FILENAME, Context.MODE_PRIVATE)));
            bw.write(stringToSave);
            bw.close();
        }
        catch (Exception e) {
            Toast toast = Toast.makeText(context, "Can not write data file!", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    public ArrayList<JogTodo> ReadTodoArray(){
        ArrayList<JogTodo> arrayListTodo = new ArrayList<>();
        try {
            JSONObject jsObj = new JSONObject(getDataFromFile(context));
            JSONArray jsArray = jsObj.getJSONArray("data");

            for (int i = 0; i < jsArray.length(); i++) {
                JSONObject jsArrayObj = jsArray.getJSONObject(i);
                JogTodo jogTodo = new JogTodo(
                        jsArrayObj.getString("title"),
                        jsArrayObj.getString("text"),
                        jsArrayObj.getString("priority"),
                        jsArrayObj.getString("date"),
                        jsArrayObj.getBoolean("isDone")
                );
                arrayListTodo.add(jogTodo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayListTodo;
    }

    private String getDataFromFile(Context context) {
        String result = "";

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    context.openFileInput(FILENAME)));
            String str = "";
            while ((str = br.readLine()) != null) {
                result += str;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(context, "Can not load data file!", Toast.LENGTH_SHORT);
            toast.show();
            return null;
        }

        return result;
    }

    public JogTodo GetJogTodo(int position){
        ArrayList<JogTodo> todoArrayList = ReadTodoArray();
        return  todoArrayList.get(position);
    }
}
