package com.example.projectcryptoapp.Retrofit;

import com.example.projectcryptoapp.model.CryptoCurrency;
import com.example.projectcryptoapp.model.MarketModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("data-api/v3/cryptocurrency/listing?start=1&limit=500")
    Call<MarketModel> getMarketModel();

}
