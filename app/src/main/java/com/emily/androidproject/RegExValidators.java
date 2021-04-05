package com.emily.androidproject;

import java.util.regex.Pattern;

public class RegExValidators {
    //my regex patterns for Validation
    public static final Pattern USER
            = Pattern.compile(
            "^[A-Z]{2}[0-9]{4}$"
    );


    public static final Pattern PASSWORD
                = Pattern.compile(
                        "^" +
                       "(?=.*[A-Z])" +
                       "(?=.*[@$!%*?&])" +
                       "(?=.*[0-9])" +
                       "(?=.*[a-z])" +
                       ".{8,}" +
                       "$"
                  );
}
