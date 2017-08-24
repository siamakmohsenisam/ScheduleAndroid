package com.example.siamakmohsenisam.schedule;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.siamakmohsenisam.schedule.model.Employee;
import com.example.siamakmohsenisam.schedule.model.Schema;
import com.example.siamakmohsenisam.schedule.model.Singleton;

public class ListActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    CheckBox[] checkBoxs = new CheckBox[4];
    ImageButton imageButtonShowL;
    ListView listViewL;

    Singleton singleton;
    SimpleCursorAdapter simpleCursorAdapter;
    Cursor cursor;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        initialize();


    }

    private void initialize() {

        singleton = (Singleton) getApplication();

        checkBoxs[0] = (CheckBox) findViewById(R.id.checkBoxL0);
        checkBoxs[1] = (CheckBox) findViewById(R.id.checkBoxL1);
        checkBoxs[2] = (CheckBox) findViewById(R.id.checkBoxL2);
        checkBoxs[3] = (CheckBox) findViewById(R.id.checkBoxL3);

        imageButtonShowL = (ImageButton) findViewById(R.id.imageButtonShowL);

        imageButtonShowL.setOnClickListener(this);

        listViewL = (ListView) findViewById(R.id.listViewL);
        listViewL.setOnItemClickListener(this);
        initAdapter(new String[] {"0000"});
    }

    private void initAdapter(String[] value) {
        cursor = singleton.queryDatabaseValue(Schema.AVAILEBLE.getValue(),value);

            simpleCursorAdapter = new
                    SimpleCursorAdapter(this,
                    R.layout.one_cell,
                    cursor,
                    new String[]{Schema.NAME.getValue(), Schema.PHONE.getValue(), Schema.EMAIL.getValue()},
                    new int[]{R.id.textViewNameCell, R.id.textViewPhoneCell, R.id.textViewEmailCell}, 0);
            listViewL.setAdapter(simpleCursorAdapter);
            simpleCursorAdapter.changeCursor(cursor);
            simpleCursorAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageButtonShowL:
                String av="";
                for (int i=0 ; i<4 ;i++) {
                    if (checkBoxs[i].isChecked())
                        av +="1";
                    else av +="0";
                }

                String[] value = {av};
                initAdapter(value);

                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Employee employee = new Employee();


        intent = new Intent(this,TaskAssignActivity.class);

        /*

        old employee
         */
        cursor.moveToFirst();

        if (cursor.getCount()>0 )
        {

            for (int i=0;i<position;i++)
                cursor.moveToNext();

            employee.setEmail(cursor.getString(1));
            employee.setName(cursor.getString(2));
            employee.setPhone(cursor.getString(3));
            employee.setTask(cursor.getString(4));
            employee.setPassword(cursor.getString(6));

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
            intent.putExtra("employee",employee);
            startActivity(intent);
        }

        /*
        error
         */
    }
}









