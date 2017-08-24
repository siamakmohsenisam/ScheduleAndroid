package com.example.siamakmohsenisam.schedule.model;

/**
 * Created by siamakmohsenisam on 2017-06-26.
 */

public enum EmployeeValue {
    CLASSNAME("employee"),
    NAME("name"),
    PHONE("phone"),
    EMAIL("email"),
    TASK("task"),
    AVAILEBLE("availeble"),
    PASSWORD("password");

    private final String value;

    EmployeeValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
