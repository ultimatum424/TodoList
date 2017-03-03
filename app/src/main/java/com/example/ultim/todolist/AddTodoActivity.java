package com.example.ultim.todolist;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class AddTodoActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editHeader;
    EditText editDescription;
    EditText editDate;
    Calendar myCalendar;
    Button buttonSave;
    Spinner spinner;
    FileManager fileManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fileManager = new FileManager(this);
        setContentView(R.layout.activity_add_todo);
        editHeader = (EditText) findViewById(R.id.editHeader);
        editDescription = (EditText) findViewById(R.id.editDescription);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        spinner = (Spinner) findViewById(R.id.spinnerPriority) ;
        myCalendar = Calendar.getInstance();
        editDate = (EditText) findViewById(R.id.editDate);
        editDate.setOnClickListener(this);
        buttonSave.setOnClickListener(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null){
            JogTodo jogTodo = (JogTodo) bundle.getSerializable("object");
            editHeader.setText(jogTodo.title);
            editDescription.setText(jogTodo.text);
            editDate.setText(jogTodo.date);

            

        }
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    private void updateLabel() {
        String myFormat = "dd MMM yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editDate :
            {
                new DatePickerDialog(AddTodoActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                return;
            }
            case R.id.buttonSave :
            {
                String priority = "";
                if (spinner.getSelectedItemPosition() == 0){
                    priority = "low";
                }
                if (spinner.getSelectedItemPosition() == 1){
                    priority = "medium";
                }
                if (spinner.getSelectedItemPosition() == 2){
                    priority = "max";
                }
            JogTodo jogTodo = new JogTodo(editHeader.getText().toString(), editDescription.getText().toString(),
                    priority, editDate.getText().toString(), false);
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("object", jogTodo);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
            }
        }

    }
}
