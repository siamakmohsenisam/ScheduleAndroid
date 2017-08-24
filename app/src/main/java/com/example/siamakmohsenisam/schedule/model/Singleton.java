package com.example.siamakmohsenisam.schedule.model;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStreamReader;

/**
 * Created by siamakmohsenisam on 2017-06-25.
 */

public class Singleton extends Application {

    private SQLiteDatabase myDb = null;
    private DatabaseOpenHelper databaseOpenHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        databaseOpenHelper = new DatabaseOpenHelper(this);
        myDb = databaseOpenHelper.getWritableDatabase();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        myDb.close();
        databaseOpenHelper.close();
    }

    public long insertDatabaseValue(ContentValues values){
        return myDb.insert(Schema.TABLE_NAME.getValue(),null,values);
    }
    public int updateDatabaseValue(ContentValues values, String[] emails ){
        return myDb.update(Schema.TABLE_NAME.getValue(),values,Schema.EMAIL.getValue()+"=?",emails);
    }
    public long deleteDatabaseValue(ContentValues values, String[] emails ){
        return myDb.update(Schema.TABLE_NAME.getValue(),values,Schema.EMAIL.getValue()+"=?",emails);
    }
    public Cursor queryDatabaseValue(String column , String[] values ){
        return myDb.query(Schema.TABLE_NAME.getValue(),Schema.COLUMNS.getValue().split(",")
                ,column+"=?",values,null,null,null);
    }
    public void deleteDatabase(){

        this.deleteDatabase(Schema.DATABASE_NAME.getValue());
        Log.d("DATABASE","The database "+Schema.DATABASE_NAME.getValue()+" is removed successfully");
    }
    public ContentValues makeContentValue(Employee employee) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Schema.NAME.getValue(),employee.getName());
        contentValues.put(Schema.EMAIL.getValue(),employee.getEmail());
        contentValues.put(Schema.PHONE.getValue(),employee.getPhone());
        contentValues.put(Schema.TASK.getValue(),employee.getTask());
        contentValues.put(Schema.PASSWORD.getValue(),employee.getPassword());
        String av="";
        for (int i=0 ; i<4 ;i++) {
            if (employee.getAvailable()[i])
                av +="1";
            else av +="0";
        }
        contentValues.put(Schema.AVAILEBLE.getValue(),av);
        return contentValues;
    }







    public void loadXmlFromFile(Context context, String fileName) throws Exception {

        AssetManager assetManager = context.getResources().getAssets();
        InputStreamReader inputStreamReader = new InputStreamReader(assetManager.open(fileName));

        XmlPullParserFactory factory = null;
        factory = XmlPullParserFactory.newInstance();
        XmlPullParser xmlPullParser = factory.newPullParser();

        xmlPullParser.setInput(inputStreamReader);

        Employee employee= null;
        Boolean[] available = null;

        while (xmlPullParser.getEventType()!= XmlPullParser.END_DOCUMENT) {

            if (xmlPullParser.getEventType() == XmlPullParser.START_TAG) {

                switch (xmlPullParser.getName()) {
                    case "employee":
                        employee = new Employee();
                        break;
                    case "name":
                        xmlPullParser.next();
                            employee.setName(xmlPullParser.getText());
                        break;
                    case "phone":
                        xmlPullParser.next();
                        try{
                        employee.setPhone(employee.makePhone(xmlPullParser.getText()));
                         }catch (Exception e){employee.setPhone("+1(111)111-1111");}
                        break;
                    case "email":
                        xmlPullParser.next();
                        try{
                        employee.setEmail(xmlPullParser.getText());
                        }catch (Exception e){employee.setEmail("input@wrong.com");}
                        break;
                    case "task":
                        xmlPullParser.next();
                        employee.setTask(xmlPullParser.getText());
                        break;
                    case "available":
                       available = new Boolean[4];
                        break;
                    case "a1":
                        xmlPullParser.next();
                        if (xmlPullParser.getText().equals("y"))
                             available[0]= true;
                        else available[0]= false;
                        break;
                    case "a2":
                        xmlPullParser.next();
                        if (xmlPullParser.getText().equals("y"))
                            available[1]= true;
                        else available[1]= false;
                        break;
                    case "a3":
                        xmlPullParser.next();
                        if (xmlPullParser.getText().equals("y"))
                            available[2]= true;
                        else available[2]= false;
                        break;
                    case "a4":
                        xmlPullParser.next();
                        if (xmlPullParser.getText().equals("y"))
                            available[3]= true;
                        else available[3]= false;
                        break;
                }
            }
            if (xmlPullParser.getEventType() == XmlPullParser.END_TAG )  {

                switch (xmlPullParser.getName()) {

                    case "employee":
                        //save to database  *****
                        insertDatabaseValue(makeContentValue(employee));
                        break;
                    case "available":
                        employee.setAvailable(available);
                        break;
                }
            }
            xmlPullParser.next();
        }
    }


/*

        <name>zhao</name>
        <phone>5148340123</phone>
        <email>zhaoxiaozhao@gmail.com</email>
        <task></task>
        <available>
            <a1>y</a1>
            <a2>y</a2>
            <a3>y</a3>
            <a4>y</a4>
        </available>
 */

}
