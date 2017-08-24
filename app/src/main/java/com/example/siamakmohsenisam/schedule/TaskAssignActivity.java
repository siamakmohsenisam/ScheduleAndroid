package com.example.siamakmohsenisam.schedule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siamakmohsenisam.schedule.model.Employee;
import com.example.siamakmohsenisam.schedule.model.Singleton;

public class TaskAssignActivity extends AppCompatActivity implements View.OnClickListener{

    TextView textViewNameS,textViewPhoneS,textViewEmailS ;
    EditText editTextTaskS;
    ImageButton imageButtonReturnS, imageButtonSaveS;
    Singleton singleton;
    Employee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_assign);


        initialize();
        firstFill();
    }

    private void initialize() {

        singleton = (Singleton) getApplication();
        textViewEmailS = (TextView) findViewById(R.id.textViewEmailS);
        textViewNameS = (TextView) findViewById(R.id.textViewNameS);
        textViewPhoneS = (TextView) findViewById(R.id.textViewPhoneS);

        editTextTaskS = (EditText) findViewById(R.id.editTextTaskS);

        imageButtonReturnS = (ImageButton) findViewById(R.id.imageButtonReturnS);
        imageButtonSaveS = (ImageButton) findViewById(R.id.imageButtonSaveS);

        imageButtonSaveS.setOnClickListener(this);
        imageButtonReturnS.setOnClickListener(this);



    }
    private void firstFill() {

        if (getIntent().getExtras()!=null) {
            employee = (Employee) getIntent().getExtras().getSerializable("employee");

            textViewEmailS.setText(employee.getEmail());
            textViewNameS.setText(employee.getName());
            textViewPhoneS.setText(employee.getPhone());
            editTextTaskS.setText(employee.getTask());
        }

    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.imageButtonReturnS:
                finish();
                break;
            case R.id.imageButtonSaveS:
                employee.setTask(employee.getTask()+editTextTaskS.getText());
                singleton.updateDatabaseValue(singleton.makeContentValue(employee),new String[]{employee.getEmail()});
                Toast.makeText(this,"your task was saved",Toast.LENGTH_LONG).show();

                break;
        }
    }



}
