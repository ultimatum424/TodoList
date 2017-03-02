package com.example.ultim.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PreviewTodoActivity extends AppCompatActivity{
    TextView textTitle;
    TextView textText;
    TextView textDate;
    TextView textPriority;
    Button buttonEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_todo);
        textTitle = (TextView) findViewById(R.id.preview_title);
        textText = (TextView) findViewById(R.id.preview_text);
        textDate = (TextView) findViewById(R.id.preview_date);
        textPriority = (TextView) findViewById(R.id.preview_priority);
        buttonEdit = (Button) findViewById(R.id.preview_button_edit);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getBaseContext(), AddTodoActivity.class);
                JogTodo jogTodo = new JogTodo(textTitle.getText().toString(), textText.getText().toString(),
                        textPriority.getText().toString(),textDate.getText().toString(), false);
                Bundle bundle = new Bundle();
                bundle.putSerializable("object", jogTodo);
                myIntent.putExtras(bundle);
                startActivityForResult(myIntent, 1);
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null){
            JogTodo jogTodo = (JogTodo) bundle.getSerializable("object");
            textTitle.setText(jogTodo.title);
            textText.setText(jogTodo.text);
            textDate.setText(jogTodo.date);
            if (jogTodo.priority.equals("max")){
                textPriority.setText("Высокий");
            }
            if (jogTodo.priority.equals("medium")){
                textPriority.setText("Обычный");
            }
            if (jogTodo.priority.equals("low")){
                textPriority.setText("Низкий");
            }
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
        textTitle.setText(jogTodo.title);
        textText.setText(jogTodo.text);
        textDate.setText(jogTodo.date);
        if (jogTodo.priority.equals("max")){
            textPriority.setText("Высокий");
        }
        if (jogTodo.priority.equals("medium")){
            textPriority.setText("Обычный");
        }
        if (jogTodo.priority.equals("low")){
            textPriority.setText("Низкий");
        }
    }
}
