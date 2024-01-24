package com.app.util;

import com.app.model.User;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {
    public static Boolean isValueNotNullAndEmpty(Object key) {
        return key != null && !key.toString().trim().isEmpty();
    }

    public static String getEncodedPassword(String plainPassword){
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public static Boolean isPasswordValid(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    public static User getUserFromSession(HttpSession httpSession){
        return (User) httpSession.getAttribute("user");
    }

    public static Date getDateFromString (String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getUserFullName(HttpSession httpSession) {
        String userFullName = "";
        try{
            User user = (User) httpSession.getAttribute("user");
            if(user != null) {
                if(isValueNotNullAndEmpty(user.getFirstName())) {
                    userFullName += user.getFirstName();
                }
                if(isValueNotNullAndEmpty(user.getSurname())) {
                    userFullName += " " + user.getSurname();
                }
            }
            return userFullName;
        }
        catch (Exception e) {
            return userFullName;
        }
    }
}
