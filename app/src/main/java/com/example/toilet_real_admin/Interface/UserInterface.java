package com.example.toilet_real_admin.Interface;

import com.example.toilet_real_admin.Model.StatusCode;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserInterface {
    @POST("/toilet_map/login.php")
    @FormUrlEncoded
    Call<ResponseBody> loginUser(@FieldMap Map<String,String> params);


}
