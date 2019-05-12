package com.example.pinpointer.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
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
import com.example.pinpointer.Fragment.SearchFragment;
import com.example.pinpointer.Fragment.VerifyLocationFragment;
import com.example.pinpointer.LocaleHelper;
import com.example.pinpointer.Main;
import com.example.pinpointer.R;

import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private NavigationView navigationView;
    Fragment fragment;
    TextView heyTv,below;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String longitude = "longitude";
    public static final String latitude = "latitude";
    SharedPreferences sharedpreferences;

    SharedPreferences.Editor editor;


    private ImageButton search;

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
        heyTv=findViewById(R.id.hey_tv);
        below=findViewById(R.id.below);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        Paper.init(this);
        search = findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chekNetwork();
                EditText searchKey = findViewById(R.id.searchKey);
                Bundle bundle = new Bundle();
                bundle.putString("search", searchKey.getText().toString());


                fragment = new SearchFragment();
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                Toast.makeText(MainActivity.this, searchKey.getText().toString(), Toast.LENGTH_LONG).show();
            }
        });


        getLocation();


    }
    @SuppressLint("ResourceAsColor")
    public void chekNetwork(){
        if(!isNetworkAvailable()){

            Toast toast= Toast.makeText(getApplicationContext(),"Ops!! check Your Connection",Toast.LENGTH_LONG);
            View view =toast.getView();
            view.setBackgroundResource(R.color.amber_500);

            TextView textView=view.findViewById(android.R.id.message);

            textView.setTextColor(R.color.white);
            toast.show();
        }

    }

    public void getLocation() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.ipfind.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LocationPlaceHolder locationPlaceHolder = retrofit.create(LocationPlaceHolder.class);


        Call<Location> call = locationPlaceHolder.getLocation();

        call.enqueue(new Callback<Location>() {
            @Override
            public void onResponse(Call<Location> call, Response<Location> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                Location location = response.body();
                editor.putString(latitude, location.getLatitude());
                editor.putString(longitude, location.getLongitude());
                editor.commit();
                TextView tv = findViewById(R.id.longitude);
                tv.setText("Longitude " + sharedpreferences.getString(longitude, "Unable to get longitude"));
                TextView tv2 = findViewById(R.id.latitude);
                tv2.setText("Latitude " + sharedpreferences.getString(latitude, "Unable to get latitude"));


            }

            @Override
            public void onFailure(Call<Location> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
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
                break;
            case R.id.addLocation:
                Intent i = new Intent(MainActivity.this, AddLocation.class);
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

    private void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void updateView(String lang) {
        Context context = LocaleHelper.setLocale(getApplicationContext(), lang);
        Resources resources = context.getResources();
        heyTv.setText(resources.getString(R.string.hey_there));
        below.setText(resources.getString(R.string.find_nearby_places));
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }}