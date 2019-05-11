package com.example.pinpointer.Api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LocationPlaceHolder {

    @GET("me?auth=416d709c-b459-44fa-abf2-23370e8a7a06")
    Call<Location> getLocation();
}
