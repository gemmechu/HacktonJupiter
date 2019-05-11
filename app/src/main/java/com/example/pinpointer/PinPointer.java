package com.example.pinpointer;

import android.app.Application;
import android.content.Context;

public class PinPointer  extends Application {
    public static int langCheck=0;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase,"en"));
    }

}
