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

import com.digital.banking.app.R;
import com.digital.banking.app.adapters.OperationsAdapter;
import com.digital.banking.app.adapters.models.AccountHistory;
import com.digital.banking.app.adapters.models.AccountOperation;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class Operations extends AppCompatActivity {
    RecyclerView recyclerView;
    String accountId;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<AccountOperation> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.operations);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();
        accountId = intent.getStringExtra("accountId");

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
            Call<AccountHistory> call = Authenticate.accounts.getAccountOperations(accountId, "Bearer " + Authenticate.accessToken);
            call.enqueue(new Callback<AccountHistory>() {
                @Override
                public void onResponse(@NotNull Call<AccountHistory> call, retrofit2.@NotNull Response<AccountHistory> response) {
                    if (response.isSuccessful()) {
                        AccountHistory data = response.body();
                        list.addAll(data.getAccountOperationDTOList());
                        adapter = new OperationsAdapter(Operations.this, list, accountId);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Toast.makeText(Operations.this, "An error occurred : " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AccountHistory> call, @NotNull Throwable t) {
                    Toast.makeText(Operations.this, "An error occurred !", Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(Operations.this, Accounts.class);
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.profile) {
            Intent intent = new Intent(Operations.this, Profile.class);
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.logout) {
            SharedPreferences preferences = getApplicationContext().getSharedPreferences("preferences", MODE_PRIVATE);
            preferences.edit().clear().apply();
            Intent intent = new Intent(Operations.this, Authenticate.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
