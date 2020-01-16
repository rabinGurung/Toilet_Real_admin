package com.example.toilet_real_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.toilet_real_admin.Connection.API_CALL;
import com.example.toilet_real_admin.Interface.UserInterface;
import com.example.toilet_real_admin.Model.StatusCode;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import okhttp3.internal.Internal;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText username, password;
    private Button btnlogin;
    private Retrofit retrofit;
    private UserInterface userInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnlogin = findViewById(R.id.btnlogin);
        btnlogin.setOnClickListener(this);
        retrofit = API_CALL.getAPI_Instance().getRetrofit();
        userInterface = retrofit.create(UserInterface.class);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnlogin){
            if(TextUtils.isEmpty(username.getText())){
                username.setError("Please provide username!");
                return;
            }

            if(TextUtils.isEmpty(password.getText())){
                password.setError("Please provide password!");
                return;
            }

            HashMap<String, String> map = new HashMap<>();
            map.put("username",username.getText().toString());
            map.put("password",password.getText().toString());

            Call<ResponseBody> call = userInterface.loginUser(map);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    int code = response.code();
                    switch (code){
                        case 200:
                            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this,Dashboard.class));
                            break;
                        case 404:
                            Toast.makeText(MainActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 409:
                            Toast.makeText(MainActivity.this, "Password wrong", Toast.LENGTH_SHORT).show();
                            break;
                        case 400:
                            Toast.makeText(MainActivity.this, "Server Failed", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
