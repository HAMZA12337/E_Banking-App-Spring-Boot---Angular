package com.digital.banking.app.interfaces;

import com.digital.banking.app.adapters.models.AccountHistory;
import com.digital.banking.app.adapters.models.AccountOperation;
import com.digital.banking.app.adapters.models.BankAccount;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface Accounts {
    @GET("/accounts/{id}")
    Call<BankAccount> getSingleAccount(@Path("id") String id, @Header("Authorization") String token);

    @GET("/accounts/{id}/pageOperations")
    Call<AccountHistory> getAccountOperations(@Path("id") String id, @Header("Authorization") String token);

    @POST("/operations/Debit")
    Call<AccountOperation> debitAnAccount(@Body AccountOperation operation, @Header("Authorization") String token);

    @POST("/operations/Credit")
    Call<AccountOperation> creditAnAccount(@Body AccountOperation operation, @Header("Authorization") String token);

    @POST("/operations/Transfers")
    Call<Void> transferToAnAccount(@Query("idSource") String idSource,@Query("idDestination") String idDestination,@Query("amount") double amount, @Header("Authorization") String token);
}
