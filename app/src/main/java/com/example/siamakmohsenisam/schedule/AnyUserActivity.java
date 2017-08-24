package com.example.siamakmohsenisam.schedule;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.siamakmohsenisam.schedule.model.Employee;
import com.example.siamakmohsenisam.schedule.model.Singleton;

public class AnyUserActivity extends AppCompatActivity implements View.OnClickListener,DialogInterface.OnClickListener {

    EditText editTextNameR, editTextPhoneR, editTextEmailR;
    CheckBox[] checkBoxs = new CheckBox[4];
    TextView textViewTaskR;
    ImageButton imageButtonRegisterR, imageButtonShowR;
    Employee employee;
    Singleton singleton;
    AlertDialog alertDialog;
    AlertDialog.Builder aBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_any_user);

        initialize();
        initAlert();
    }
    private void initAlert() {
        aBuilder = new  AlertDialog.Builder(this);
        aBuilder.setTitle("your input is not correct please try again");
        aBuilder.setNegativeButton("Ok", this);
        alertDialog = aBuilder.create();
    }

    private void initialize() {

        singleton = (Singleton) getApplication();
        editTextNameR = (EditText) findViewById(R.id.editTextNameR);
        editTextPhoneR = (EditText) findViewById(R.id.editTextPhoneR);
        editTextEmailR = (EditText) findViewById(R.id.editTextEmailR);

        checkBoxs[0] = (CheckBox) findViewById(R.id.checkBoxR0);
        checkBoxs[1] = (CheckBox) findViewById(R.id.checkBoxR1);
        checkBoxs[2] = (CheckBox) findViewById(R.id.checkBoxR2);
        checkBoxs[3] = (CheckBox) findViewById(R.id.checkBoxR3);

        textViewTaskR = (TextView) findViewById(R.id.textViewTaskR);

        imageButtonRegisterR = (ImageButton) findViewById(R.id.imageButtonRegisterR);
        imageButtonShowR = (ImageButton) findViewById(R.id.imageButtonShowR);

        imageButtonRegisterR.setOnClickListener(this);
        imageButtonShowR.setOnClickListener(this);

       firstFill();

    }

    private void firstFill() {
        if (getIntent().getExtras()!=null){
            employee = (Employee) getIntent().getExtras().getSerializable("employee");
            if (getIntent().getExtras().getInt("tag")== 1){

                editTextEmailR.setText(employee.getEmail());
                editTextNameR.setText(employee.getName());
                editTextPhoneR.setText(employee.getPhone());
                textViewTaskR.setText(employee.getTask());
                for (int i=0;i<4;i++) {
                    checkBoxs[i].setChecked(employee.getAvailable()[i]);
                    checkBoxs[i].setEnabled(false);
                }

                editTextNameR.setEnabled(false);
                editTextPhoneR.setEnabled(false);
                imageButtonRegisterR.setEnabled(false);
            }
            else {
                editTextEmailR.setText(employee.getEmail());

            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageButtonRegisterR:
                if (editTextNameR.getText().toString().equals("")) {
                    alertDialog.show();
                    return;
                }
                try {
                    employee.setName(editTextNameR.getText().toString());

                    Boolean[] available= new Boolean[4];
                    for (int i=3 ; i>=0 ;i--) {
                        if (checkBoxs[i].isChecked())
                            available[i] = true;
                        else available[i] = false;
                    }
                    employee.setPhone(editTextPhoneR.getText().toString());
                    employee.setAvailable(available);

                }catch (Exception e){
                    alertDialog.show();
                    return;
                }

                singleton.insertDatabaseValue(singleton.makeContentValue(employee));
                Toast.makeText(this,"your employee was saved",Toast.LENGTH_LONG).show();
                finish();
                break;
            case R.id.imageButtonShowR:

                break;
        }
    }



    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
}
