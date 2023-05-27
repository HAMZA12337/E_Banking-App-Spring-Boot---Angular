package com.digital.banking.app.interfaces;

import java.util.HashMap;
import java.util.List;

import com.digital.banking.app.adapters.models.BankAccount;
import com.digital.banking.app.adapters.models.Customer;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface Customers {
    @POST("login")
    Call<HashMap<String, Object>> authenticate(@Body HashMap<String, Object> user);

    @GET("refreshToken")
    Call<HashMap<String, Object>> refreshTheToken();

    @PUT("customers/{id}")
    Call<Customer> updateACustomer(@Path("id") long id, @Body com.digital.banking.app.adapters.models.Customer item, @Header("Authorization") String token);

    @GET("customers/{id}")
    Call<Customer> getASingleCustomer(@Path("id") int id, @Header("Authorization") String token);

    @GET("customers/{id}/accounts")
    Call<List<BankAccount>> getBankAccountsOfACustomer(@Path("id") int id, @Header("Authorization") String token);

}
