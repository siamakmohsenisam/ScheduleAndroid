package com.example.siamakmohsenisam.schedule;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.siamakmohsenisam.schedule.model.Employee;
import com.example.siamakmohsenisam.schedule.model.Schema;
import com.example.siamakmohsenisam.schedule.model.Singleton;

public class ConnectionActivity extends AppCompatActivity implements View.OnClickListener ,DialogInterface.OnClickListener{

    EditText editTextEmail1,editTextPassword1;
    ImageButton buttonConnect;
    Intent intent;
    AlertDialog alertDialog;
    AlertDialog.Builder aBuilder;

    Singleton singleton;
    Cursor cursor;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

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

        editTextEmail1 = (EditText) findViewById(R.id.editTextEmail1);
        editTextPassword1 = (EditText) findViewById(R.id.editTextPassword1);
        buttonConnect = (ImageButton) findViewById(R.id.buttonConnect);

        buttonConnect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonConnect:
                if (editTextEmail1.getText().toString().equals("")||
                        editTextPassword1.getText().toString().equals("") ) {
                    alertDialog.show();
                    return;
                }
                if (editTextEmail1.getText().toString().toLowerCase().equals("admin") &&
                        editTextPassword1.getText().toString().toLowerCase().equals("admin")) {
                    admin();
                    return;
                }
                if (editTextEmail1.getText().toString().toLowerCase().equals("manager") &&
                        editTextPassword1.getText().toString().toLowerCase().equals("manager")){
                    manager();
                    return;
                }
                user();
                break;
            case R.id.imageButtonCancelD:
                dialog.dismiss();
                break;
            case R.id.imageButtonLoadD:
                load();
                Toast.makeText(this,"your file was loaded",Toast.LENGTH_LONG).show();
                dialog.dismiss();
                break;
            case R.id.imageButtonDeleteD:
                singleton.deleteDatabase();
                Toast.makeText(this,"your database was deleted",Toast.LENGTH_LONG).show();
                finish();
                break;
        }
    }

    private void load() {
        try {
            singleton.loadXmlFromFile(this,"employees.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void user() {

        Employee employee = new Employee();
        String[] value = {editTextEmail1.getText().toString()};
        try {
            employee.setEmail(editTextEmail1.getText().toString());
            employee.setPassword(editTextPassword1.getText().toString());
        }catch (Exception e){
            alertDialog.show();
            return;
        }

        intent = new Intent(this,AnyUserActivity.class);

        cursor = singleton.queryDatabaseValue(Schema.EMAIL.getValue(),value);
        cursor.moveToFirst();

        /*

        old employee
         */
        if (cursor.getCount()>0 &&
                cursor.getString(1).equals(employee.getEmail())&&
                (cursor.getString(6).equals(employee.getPassword())||cursor.getString(6).equals("")))
        {
            employee.setName(cursor.getString(2));
            employee.setPhone(cursor.getString(3));
            employee.setTask(cursor.getString(4));

            int av = Integer.valueOf(cursor.getString(5));
            Boolean[] available = new Boolean[4];
            for (int i=3 ; i>=0 ;i--)
            {
                if (av%10 == 0)
                    available[i]= false;
                else available[i]= true;
                av /= 10;
            }
            employee.setAvailable(available);
            if (cursor.getString(6).equals(""))
                singleton.updateDatabaseValue(singleton.makeContentValue(employee),new String[]{employee.getEmail()});

            intent.putExtra("employee",employee);
            intent.putExtra("tag",1);
            startActivity(intent);
        }
        /*
        new employee

         */
        if (cursor.getCount()<= 0){
            intent.putExtra("employee",employee);
            intent.putExtra("tag",0);
            startActivity(intent);

        }
        /*
        error
         */

    }

    private void admin() {
        intent = new Intent(this,ListActivity.class);
        startActivity(intent);
    }

    private void manager() {
        dialog = new Dialog(ConnectionActivity.this);
        dialog.setContentView(R.layout.dialog_manager);
        dialog.setTitle("dialog for load");
        ImageButton imageButtonCancelD = (ImageButton) dialog.findViewById(R.id.imageButtonCancelD);
        ImageButton imageButtonLoadD = (ImageButton) dialog.findViewById(R.id.imageButtonLoadD);
        ImageButton imageButtonDeleteD = (ImageButton) dialog.findViewById(R.id.imageButtonDeleteD);

        imageButtonCancelD.setOnClickListener(this);
        imageButtonLoadD.setOnClickListener(this);
        imageButtonDeleteD.setOnClickListener(this);
        dialog.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
}
