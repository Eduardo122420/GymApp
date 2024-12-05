package com.example.gymapp;

import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;

public class Utility {

    static String timestamptostring(Timestamp timestamp){
        return new SimpleDateFormat("MM/dd/yyyy").format(timestamp.toDate());
    }
}
