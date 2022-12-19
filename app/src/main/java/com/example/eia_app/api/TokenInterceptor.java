package com.example.eia_app.api;

import static com.example.eia_app.config.AppSharedPreferences.GET_ACC_TOKEN;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor{
    @NonNull
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request newRequest=chain.request().newBuilder()
                .header("Authorization","Bearer "+GET_ACC_TOKEN())
                .header("Accept","*/*")
                .header("Content-Type","application/json")
                .build();

        return chain.proceed(newRequest);
    }
}
