package com.example.siamakmohsenisam.schedule.model;

/**
 * Created by siamakmohsenisam on 2017-06-25.
 */

public enum Schema {

    DATABASE_NAME("myDatabase"),
    TABLE_NAME("employee"),
    ID("_id"),
    NAME("name"),
    PHONE("phone"),
    EMAIL("email"),
    TASK("task"),
    AVAILEBLE("availeble"),
    PASSWORD("password"),
    COLUMNS("_id,email,name,phone,task,availeble,password"),
    CREATE(create()),
    VERSION("1");

    private final String value;

    Schema(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private static String create(){

        String string="";
        string += "CREATE TABLE "+TABLE_NAME.getValue()+"(";
        string +=  ID.getValue()+ " INTEGER PRIMARY KEY,";
        string +=  EMAIL.getValue()+ " TEXT unique ,";
        string +=  NAME.getValue() + " TEXT ,";
        string +=  PHONE.getValue() + " TEXT ,";
        string +=  TASK.getValue() + " TEXT ,";
        string +=  AVAILEBLE.getValue() + " TEXT ,";
        string +=  PASSWORD.getValue() + " TEXT NOT NULL);";

        return string;
    }
}
