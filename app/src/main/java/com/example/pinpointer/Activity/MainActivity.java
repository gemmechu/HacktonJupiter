package com.example.pinpointer.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.example.pinpointer.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
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


        fragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

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
                fragment = new HomeFragment();
                break;
            case R.id.addLocation:
                Intent i = new Intent(MainActivity.this, AddLocation.class);
                startActivity(i);
                break;
            case R.id.verifyLocation:
                fragment = new VerifyLocationFragment();

                break;
            // Not implemented here

            default:
                break;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        closeDrawer();
        return true;
    }

    private void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}