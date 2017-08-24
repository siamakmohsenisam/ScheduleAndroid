package com.example.siamakmohsenisam.schedule.model;

/**
 * Created by siamakmohsenisam on 2017-06-25.
 */

public enum TypesOfValidation {


        CellPhone("^\\+\\d{1,}\\(\\d{3}\\)\\d{3}-\\d{4}$"),//+1(123)456-7890
        Email("^.+@.+\\..+$"),
        Word("^[a-zA-Z_]*$"),
        UserName("^\\w*$"),
        Date("^(\\d{3,4})-([0][1-9]|[1-9]|[1][0-2])-([1-9]|[0][1-9]|[1-2][0-9]|[3][0-1])$"),
        IntegerNumber("^\\d+$"),
        DecimalNumber("(^\\d+$)|(^\\d+\\.\\d+$)");

        private final String myPattern;

        TypesOfValidation(String myPattern) {
                this.myPattern = myPattern;
        }

        public String getMyPattern() {
                return myPattern;
        }


}
