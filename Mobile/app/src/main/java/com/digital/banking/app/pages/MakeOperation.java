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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.digital.banking.app.R;
import com.digital.banking.app.adapters.enums.OperationType;
import com.digital.banking.app.adapters.models.AccountOperation;
import com.digital.banking.app.adapters.models.BankAccount;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;

public class MakeOperation extends AppCompatActivity {
    private EditText amount, description;
    private Button validate;
    private RadioGroup operations;
    private RadioButton credit, debit, transfer;
    String accountId;
    TextView balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_operation);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();
        accountId = intent.getStringExtra("accountId");

        amount = findViewById(R.id.amount);
        description = findViewById(R.id.description);
        validate = findViewById(R.id.perform);
        operations = findViewById(R.id.operations);
        credit = findViewById(R.id.credit);
        debit = findViewById(R.id.debit);
        transfer = findViewById(R.id.transfer);

        balance = findViewById(R.id.balance);

        refreshBalance();

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checked = operations.getCheckedRadioButtonId();
                if (amount.getText().toString().trim().isEmpty() || description.getText().toString().isEmpty()) {
                    Toast.makeText(MakeOperation.this, "Please fill in all the blanks correctly !", Toast.LENGTH_SHORT).show();
                } else {
                    if (checked == credit.getId()) {
                        try {
                            AccountOperation operation = new AccountOperation();
                            operation.setAccountId(accountId);
                            operation.setAmount(Double.parseDouble(amount.getText().toString().trim()));
                            operation.setType(OperationType.CREDIT);

                            Call<AccountOperation> call = Authenticate.accounts.creditAnAccount(operation, "Bearer " + Authenticate.accessToken);
                            call.enqueue(new Callback<AccountOperation>() {
                                @Override
                                public void onResponse(@NotNull Call<AccountOperation> call, retrofit2.@NotNull Response<AccountOperation> response) {
                                    if (response.isSuccessful()) {
                                        AccountOperation data = response.body();
                                        if (data != null) {
                                            Toast.makeText(MakeOperation.this, "The credit operation executed successfully !", Toast.LENGTH_SHORT).show();
                                            refreshBalance();
                                        }
                                    } else {
                                        Toast.makeText(MakeOperation.this, "An error occurred : " + response.code(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(@NotNull Call<AccountOperation> call, @NotNull Throwable t) {
                                    Toast.makeText(MakeOperation.this, "An error occurred !", Toast.LENGTH_SHORT).show();
                                }
                            });


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (checked == debit.getId()) {
                        try {

                            AccountOperation operation = new AccountOperation();
                            operation.setAccountId(accountId);
                            operation.setAmount(Double.parseDouble(amount.getText().toString().trim()));
                            operation.setType(OperationType.DEBIT);

                            Call<AccountOperation> call = Authenticate.accounts.debitAnAccount(operation, "Bearer " + Authenticate.accessToken);
                            call.enqueue(new Callback<AccountOperation>() {
                                @Override
                                public void onResponse(@NotNull Call<AccountOperation> call, retrofit2.@NotNull Response<AccountOperation> response) {
                                    if (response.isSuccessful()) {
                                        AccountOperation data = response.body();
                                        if (data != null) {
                                            Toast.makeText(MakeOperation.this, "The debit operation executed successfully !", Toast.LENGTH_SHORT).show();
                                            refreshBalance();
                                        }
                                    } else {
                                        Toast.makeText(MakeOperation.this, "An error occurred : " + response.code(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(@NotNull Call<AccountOperation> call, @NotNull Throwable t) {
                                    Toast.makeText(MakeOperation.this, "An error occurred !", Toast.LENGTH_SHORT).show();
                                }
                            });


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (checked == transfer.getId()) {
                        try {

                            Call<Void> call = Authenticate.accounts.transferToAnAccount(accountId, accountId, Double.parseDouble(amount.getText().toString().trim()), "Bearer " + Authenticate.accessToken);
                            call.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(@NotNull Call<Void> call, retrofit2.@NotNull Response<Void> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(MakeOperation.this, "The transfer operation exxecuted successfully !", Toast.LENGTH_SHORT).show();
                                        refreshBalance();
                                    } else {
                                        Toast.makeText(MakeOperation.this, "An error occurred : " + response.code(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
                                    Toast.makeText(MakeOperation.this, "An error occurred !", Toast.LENGTH_SHORT).show();
                                }
                            });


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    private void refreshBalance() {
        Call<BankAccount> call = Authenticate.accounts.getSingleAccount(accountId, "Bearer " + Authenticate.accessToken);
        call.enqueue(new Callback<BankAccount>() {
            @Override
            public void onResponse(@NotNull Call<BankAccount> call, retrofit2.@NotNull Response<BankAccount> response) {
                if (response.isSuccessful()) {
                    BankAccount data = response.body();
                    DecimalFormat df = new DecimalFormat("0.00");
                    balance.setText("Balance : " + df.format(data.getBalance()) + " DHs");
                } else {
                    Toast.makeText(MakeOperation.this, "An error occurred : " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<BankAccount> call, @NotNull Throwable t) {
                Toast.makeText(MakeOperation.this, "An error occurred !", Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(MakeOperation.this, Accounts.class);
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.profile) {
            Intent intent = new Intent(MakeOperation.this, Profile.class);
            startActivity(intent);
            finish();
            return true;
        } else if (id == R.id.logout) {
            SharedPreferences preferences = getApplicationContext().getSharedPreferences("preferences", MODE_PRIVATE);
            preferences.edit().clear().apply();
            Intent intent = new Intent(MakeOperation.this, Authenticate.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

