package com.example.pinpointer.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pinpointer.Adapter.HomeItems;
import com.example.pinpointer.Adapter.HomeItemsAdapter;
import com.example.pinpointer.Fragment.HomeFragment;
import com.example.pinpointer.Fragment.VerifyLocationFragment;
import com.example.pinpointer.LocaleHelper;
import com.example.pinpointer.Main;
import com.example.pinpointer.R;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    TextView heyTv;
    private NavigationView navigationView;
    Fragment fragment;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = findViewById(R.id.toolbarindrawer);

        navigationView = (NavigationView) findViewById(R.id.navigationView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        final Switch langSwitch = findViewById(R.id.switch_lang);
        navigationView.setNavigationItemSelectedListener(this);
        heyTv=findViewById(R.id.hey_tv);

        fragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        Paper.init(this);
        if(!isNetworkAvailable()){

            Toast toast= Toast.makeText(getApplicationContext(),"Ops!! check Your Connection",Toast.LENGTH_LONG);
            View view =toast.getView();
            view.setBackgroundResource(R.color.amber_500);

            TextView textView=view.findViewById(android.R.id.message);

            textView.setTextColor(R.color.white);
            toast.show();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        fragment = null;
        switch (item.getItemId()) {
            case R.id.home:

                fragment = new HomeFragment();
                // Not implemented here
                break;

            case R.id.language:
        if(Main.langCheck==0){

            Paper.book().write("language","am");
            updateView((String) Paper.book().read("language"));
            Main.langCheck=1;
        }
        else{

            Paper.book().write("language","en");
            updateView((String) Paper.book().read("language"));
            Main.langCheck=0;
        }



                closeDrawer();
                return true;

            case R.id.addLocation:
                Intent i = new Intent(MainActivity.this, AddLocation.class);
                startActivity(i);
                return  true;
            case R.id.verifyLocation:
                Intent intent = new Intent(MainActivity.this, VerifyActivity.class);
                startActivity(intent);
                return  true;
            // Not implemented here

            default:
                break;
        }


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        closeDrawer();
        return true;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.language, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item){
//        switch (item.getItemId()) {
//            case R.id.menu1:
//                item.setIcon(R.drawable.ic_language_black_24dp);
//                break;
//
//            default:
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    private void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public  void updateView(String lang){
        Context context= LocaleHelper.setLocale(getApplicationContext(),lang);
        Resources resources=context.getResources();
        heyTv.setText(resources.getString(R.string.hey_there));
//DisplayMetrics displayMetrics=resources.getDisplayMetrics();
//        Configuration configuration=resources.getConfiguration();
//        resources.updateConfiguration(configuration,displayMetrics);
//        Intent i = new Intent(this,MainActivity.class);
//        startActivity(i);
//        finish();

//        loginLang.setText(resources.getString(R.string.language));
//        loginBtn.setText(resources.getString(R.string.login));
//        phoneTxt.setHint(resources.getString(R.string.phone_091215));
//        skipBtn.setText(resources.getString(R.string.skip));
    }

}