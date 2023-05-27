package com.digital.banking.app.pages;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.digital.banking.app.R;
import com.digital.banking.app.adapters.models.Customer;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;


public class Profile extends AppCompatActivity {
    private EditText customerId, name, email;
    private Button  edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        customerId = findViewById(R.id.customerId);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        edit = findViewById(R.id.edit);

        try {
            Call<Customer> call = Authenticate.customers.getASingleCustomer(1, "Bearer " + Authenticate.accessToken);
            call.enqueue(new Callback<Customer>() {
                @Override
                public void onResponse(@NotNull Call<Customer> call, retrofit2.@NotNull Response<Customer> response) {
                    if (response.isSuccessful()) {
                        Customer data = response.body();
                        customerId.setText(data.getId().toString());
                        name.setText(data.getName());
                        email.setText(data.getEmail());
                    } else {
                        Toast.makeText(Profile.this, "An error occurred : " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<Customer> call, @NotNull Throwable t) {
                    Toast.makeText(Profile.this, "An error occurred !", Toast.LENGTH_SHORT).show();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().trim().isEmpty() || email.getText().toString().isEmpty()) {
                    Toast.makeText(Profile.this, "Please fill in all the blanks correctly !", Toast.LENGTH_SHORT).show();
                } else {
                    Customer customer = new Customer();
                    customer.setEmail(email.getText().toString().trim());
                    customer.setName(name.getText().toString());
                    customer.setId(Long.parseLong(customerId.getText().toString().trim()));
                    Call<Customer> call = Authenticate.customers.updateACustomer(Long.parseLong(customerId.getText().toString().trim()), customer, "Bearer " + Authenticate.accessToken);
                    call.enqueue(new Callback<Customer>() {
                        @Override
                        public void onResponse(@NotNull Call<Customer> call, retrofit2.@NotNull Response<Customer> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(Profile.this, "The profile has been updated successfully !", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Profile.this, "An error occurred : " + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NotNull Call<Customer> call, @NotNull Throwable t) {
                            Toast.makeText(Profile.this, "An error occurred !", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.accounts) {
            Intent intent = new Intent(Profile.this, Accounts.class);
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.profile) {
            Intent intent = new Intent(Profile.this, Profile.class);
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.logout) {
            SharedPreferences preferences = getApplicationContext().getSharedPreferences("preferences", MODE_PRIVATE);
            preferences.edit().clear().apply();
            Intent intent = new Intent(Profile.this, Authenticate.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

