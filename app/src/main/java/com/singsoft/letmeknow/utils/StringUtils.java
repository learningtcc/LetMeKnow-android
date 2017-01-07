package com.singsoft.letmeknow.utils;

/**
 * Created by meidan.zemer on 1/4/2017.
 */

public class StringUtils {
    static private int passwordMinLength=8;
    static private String passwordRegExp = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)";
    static private String emailRegExp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

    static public boolean isPasswordValid(String password){
        if(password.length() < StringUtils.passwordMinLength)
            return false;
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(StringUtils.passwordRegExp);
        java.util.regex.Matcher m = p.matcher(password);
        return m.matches();
    }

    static  public boolean isEmail(String email){
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(StringUtils.emailRegExp);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}
