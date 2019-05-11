package com.example.pinpointer.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pinpointer.Api.Location;
import com.example.pinpointer.Api.LocationPlaceHolder;
import com.example.pinpointer.Fragment.HomeFragment;
import com.example.pinpointer.Fragment.VerifyLocationFragment;
import com.example.pinpointer.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private NavigationView navigationView;
    Fragment fragment;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String longitude = "longitude";
    public static final String latitude = "latitude";
    SharedPreferences sharedpreferences;

    SharedPreferences.Editor editor;


    private ImageButton search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar =  findViewById(R.id.toolbarindrawer);

        navigationView = (NavigationView) findViewById(R.id.navigationView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        final Switch langSwitch=findViewById(R.id.switch_lang);
        navigationView.setNavigationItemSelectedListener(this);
        fragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        search = findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText searchKey = findViewById(R.id.searchKey);
                Toast.makeText(MainActivity.this,searchKey.getText().toString(),Toast.LENGTH_LONG).show();
            }
        });


        getLocation();


    }

    public void getLocation(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.ipfind.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LocationPlaceHolder locationPlaceHolder = retrofit.create(LocationPlaceHolder.class);


        Call<Location> call = locationPlaceHolder.getLocation();

        call.enqueue(new Callback<Location>() {
            @Override
            public void onResponse(Call<Location> call, Response<Location> response) {
                if(!response.isSuccessful()){
                    return;
                }

                Location location = response.body();
                editor.putString(latitude, location.getLatitude());
                editor.putString(longitude, location.getLongitude());
                editor.commit();
                TextView tv = findViewById(R.id.longitude);
                tv.setText("Longitude "+ sharedpreferences.getString(longitude,"Unable to get longitude"));
                TextView tv2 = findViewById(R.id.latitude);
                tv2.setText("Latitude "+ sharedpreferences.getString(latitude,"Unable to get latitude"));


            }

            @Override
            public void onFailure(Call<Location> call, Throwable t) {
                Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

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
                Intent i = new Intent(MainActivity.this,AddLocation.class);
                startActivity(i);
                return true;
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
    private void closeDrawer(){
        drawerLayout.closeDrawer(GravityCompat.START);
    }
}
