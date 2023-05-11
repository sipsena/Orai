package com.example.orai;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    private static final String USERNAME_PATTERN = "^[A-Za-z]{3,20}$";

    //validuoja ivestus vartuotojo duomenys
    //jeigu vartuotojo ivesti duomenys atitinka auksciau aprasyta sablona
    //grazinsime TRUE, priesingu atveju FALSE
    public static boolean isUserNameValid(String username) {
        //sukuriamas sablonas pagal 7 eiluteje aprasytas taisykles
        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
        //patikrina ar vartotojo ivesti duomenys atitinka auksciau aprasyta sablona
        Matcher matcher = pattern.matcher(username);
        //True kai atitinka, false kai neatitinka
        return matcher.find();
    }

    private static final String PASSWORD_PATTERN = "^[A-Za-z0-9.!@_]{5,20}$";

        public static boolean isPasswordValid(String password) {

        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);

        return matcher.find();
    }
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9@._-]{10,50}$";

    public static boolean isEmailValid(String email) {

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);

        return matcher.find();
    }

}
