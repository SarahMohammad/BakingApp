package com.example.nano.bakingapp;

import com.example.nano.bakingapp.models.BakingModel;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;


public abstract class RestClient {


    private static GitApiInterface gitApiInterface;
    private static final String baseUrl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    public static GitApiInterface getClient() {

        OkHttpClient okClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit client = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        gitApiInterface = client.create(GitApiInterface.class);
        return gitApiInterface;
    }

    public interface GitApiInterface {

        @GET("baking.json")
        Call<ArrayList<BakingModel>> getBakings();

    }

}
