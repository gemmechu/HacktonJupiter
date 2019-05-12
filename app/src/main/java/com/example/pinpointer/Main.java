package com.example.pinpointer;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Main extends Application {


    public static int langCheck=0;
    @Override
    protected void attachBaseContext(Context newBase) {

        super.attachBaseContext(LocaleHelper.onAttach(newBase,"en"));
    }

}
