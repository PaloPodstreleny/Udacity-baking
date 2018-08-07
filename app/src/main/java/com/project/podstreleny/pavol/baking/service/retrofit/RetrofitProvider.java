package com.project.podstreleny.pavol.baking.service.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitProvider {

    private static final String BASE_MOVIE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    private static Retrofit.Builder sBuilder = new Retrofit.Builder()
            .baseUrl(BASE_MOVIE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(new LiveDataCallAdapterFactory());

    private static Retrofit sRetrofit = sBuilder.build();

    public static <T> T getService(Class<T> data) {
        return sRetrofit.create(data);
    }


}