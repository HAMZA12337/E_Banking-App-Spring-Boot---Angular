package com.digital.banking.app.pages;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.digital.banking.app.R;
import com.digital.banking.app.adapters.AccountsAdapter;
import com.digital.banking.app.adapters.models.BankAccount;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;

public class Accounts extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<BankAccount> list;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accounts);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        preferences = getApplicationContext().getSharedPreferences("preferences", MODE_PRIVATE);

        list = new ArrayList<>();
        recyclerView = findViewById(R.id.recycleViewContainer);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        fetch();
    }

    public void fetch() {
        list.clear();

        try {
            Call<List<BankAccount>> call = Authenticate.customers.getBankAccountsOfACustomer(1, "Bearer " + Authenticate.accessToken);
            call.enqueue(new Callback<List<BankAccount>>() {
                @Override
                public void onResponse(@NotNull Call<List<BankAccount>> call, retrofit2.@NotNull Response<List<BankAccount>> response) {
                    if (response.isSuccessful()) {
                        List<BankAccount> data = response.body();
                        list.addAll(data);
                        adapter = new AccountsAdapter(Accounts.this, list);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(Accounts.this, "An error occurred : " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<List<BankAccount>> call, @NotNull Throwable t) {
                    Toast.makeText(Accounts.this, "An error occurred !", Toast.LENGTH_SHORT).show();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
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
            Intent intent = new Intent(Accounts.this, Accounts.class);
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.profile) {
            Intent intent = new Intent(Accounts.this, Profile.class);
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.logout) {
            preferences.edit().clear().apply();
            Intent intent = new Intent(Accounts.this, Authenticate.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
