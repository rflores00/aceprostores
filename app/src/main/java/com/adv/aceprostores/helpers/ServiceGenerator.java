package com.adv.aceprostores.helpers;

import android.content.Context;

import com.adv.aceprostores.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ruben Flores on 10/29/2016.
 */

public class ServiceGenerator {
    public static Retrofit retrofit;

    public static <S> S createService(Class<S> serviceClass, final Context c) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        OkHttpClient client = httpClient.build();

        Gson builder1 = new GsonBuilder().setLenient().create();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(c.getString(R.string.conf_api_base_url))
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create(builder1));

        retrofit = builder.client(httpClient.build()).build();

        return retrofit.create(serviceClass);
    }
}
