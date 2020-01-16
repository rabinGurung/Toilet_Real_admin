package com.example.toilet_real_admin.Interface;


import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Visitors {
    @GET("toilet_map/jan.php")
    Call<ResponseBody> getJan();

    @GET("/toilet_map/dec.php")
    Call<ResponseBody> getDec();

    @GET("/toilet_map/feb.php")
    retrofit2.Call<ResponseBody> getFeb();
}
