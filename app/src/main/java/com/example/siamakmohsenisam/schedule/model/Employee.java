package com.example.siamakmohsenisam.schedule.model;

import java.io.Serializable;

/**
 * Created by siamakmohsenisam on 2017-06-25.
 */

public class Employee implements Serializable{

    private String name;
    private String phone;
    private String email;
    private String password;
    private String task;
    private Boolean[] available ;

    public Employee() {
        this("","","","","",new Boolean[]{false, false, false, false});
    }

    public Employee(String name, String phone, String email, String password, String task, Boolean[] available) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.task = task;
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
            if(name.length()>1)
                name=name.substring(0, 1).toUpperCase()+
                        name.substring(1).toLowerCase();
            this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if(phone.matches(TypesOfValidation.CellPhone.getMyPattern())){
            this.phone = phone;
        }
        else throw new IllegalArgumentException("your cellPhone number don't have correct word ");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(email.matches(TypesOfValidation.Email.getMyPattern())){
            this.email = email;
        }
        else throw new IllegalArgumentException("your Email don't have correct word ");
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(password.matches(TypesOfValidation.UserName.getMyPattern())){
            this.password = password;
        }
        else throw new IllegalArgumentException("your password don't have correct word ");
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Boolean[] getAvailable() {
        return available;
    }

    public void setAvailable(Boolean[] available) {
        this.available = available;
    }

    public String makePhone(String phone){
        char[] chars =  phone.toCharArray();
        String str="+1(";
        str += phone.substring(0,3)+")";
        str += phone.substring(3,6)+"-";
        str += phone.substring(6,10);
        return str;
    }

    @Override
    public String toString() {
        return  name + " , " + phone + " , " + email ;
    }
}
