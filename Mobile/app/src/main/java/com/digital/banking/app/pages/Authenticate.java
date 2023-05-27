package com.digital.banking.app.pages;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import com.digital.banking.app.R;
import com.digital.banking.app.interfaces.Accounts;
import com.digital.banking.app.interfaces.Customers;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Authenticate extends AppCompatActivity {
    private SharedPreferences preferences;
    public static String accessToken;
    public static Customers customers;
    public static Accounts accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authenticate);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        preferences = getApplicationContext().getSharedPreferences("preferences", MODE_PRIVATE);
        final EditText usernameEditText = findViewById(R.id.email);
        final EditText passwordEditText = findViewById(R.id.password);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.api_base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        customers = retrofit.create(Customers.class);
        accounts = retrofit.create(Accounts.class);

        Button logIn = findViewById(R.id.log_in);

        if (preferences.contains("role")) {
            Authenticate.accessToken = preferences.getString("access", "null");
            Intent intent = new Intent(getApplicationContext(), com.digital.banking.app.pages.Accounts.class);
            startActivity(intent);
        }

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String username = usernameEditText.getText().toString().trim();
                final String password = passwordEditText.getText().toString().trim();
                if (username.trim().isEmpty() || password.trim().isEmpty()) {
                    Toast.makeText(Authenticate.this, "Please fill in all the blanks correctly !", Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, Object> user = new HashMap<>();
                    user.put("username", username);
                    user.put("password", password);
                    Call<HashMap<String, Object>> call = Authenticate.customers.authenticate(user);
                    call.enqueue(new Callback<HashMap<String, Object>>() {
                        @Override
                        public void onResponse(@NotNull Call<HashMap<String, Object>> call, retrofit2.@NotNull Response<HashMap<String, Object>> response) {
                            if (response.isSuccessful()) {
                                HashMap<String, Object> data = response.body();

                                if (data.get("jwt") != null) {
                                    Authenticate.accessToken = data.get("jwt").toString();
                                    preferences.edit().putString("access", data.get("jwt").toString()).apply();
                                    preferences.edit().putString("refresh", data.get("refreshToken").toString()).apply();
                                    if (data.get("roles").toString().contains("CUSTOMER")) {
                                        preferences.edit().putString("id", data.get("id").toString()).apply();
                                    }
                                    preferences.edit().putString("role", data.get("roles").toString()).apply();
                                    Toast.makeText(Authenticate.this, "Signed in successfully !", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), com.digital.banking.app.pages.Accounts.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                Toast.makeText(Authenticate.this, "The given information are incorrect, please check and try again !", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NotNull Call<HashMap<String, Object>> call, @NotNull Throwable t) {
                            Toast.makeText(Authenticate.this, "An error occurred !", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

    }
}
