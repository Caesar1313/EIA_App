package com.example.eia_app.api;

import static com.example.eia_app.config.AppSharedPreferences.GET_ACC_TOKEN;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.example.eia_app.App;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    public static String BASE_URL = "https://10.0.2.2:8080/";

    public static Retrofit retrofit;

    private static APIInterface apiInterface = null;

    private static TokenInterceptor interceptor = new TokenInterceptor();

    public static OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
        Request newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer " + GET_ACC_TOKEN())
                .build();
        return chain.proceed(newRequest);
    }).build();

    public static Retrofit getRetrofitInstance() {
        return retrofit = new Retrofit.Builder()
                .client(getUnsafeOkHttpClient())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static APIInterface getAPI() {
        if (apiInterface == null)
            apiInterface = getRetrofitInstance().create(APIInterface.class);
        return apiInterface;
    }

    public static OkHttpClient getUnsafeOkHttpClient() {
        try {
            TrustManager[] arrayOfTrustManager = new TrustManager[1];
            X509TrustManager x509TrustManager = new X509TrustManager() {
                @SuppressLint("TrustAllX509TrustManager")
                public void checkClientTrusted(X509Certificate[] param1ArrayOfX509Certificate, String param1String) throws CertificateException {
                }

                @SuppressLint("TrustAllX509TrustManager")
                public void checkServerTrusted(X509Certificate[] param1ArrayOfX509Certificate, String param1String) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };

            arrayOfTrustManager[0] = x509TrustManager;
            SSLContext sSLContext = SSLContext.getInstance("SSL");
            SecureRandom secureRandom = new SecureRandom();

            sSLContext.init(null, arrayOfTrustManager, secureRandom);
            SSLSocketFactory sSLSocketFactory = sSLContext.getSocketFactory();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            builder.sslSocketFactory(sSLSocketFactory, (X509TrustManager) arrayOfTrustManager[0]);
            HostnameVerifier hostnameVerifier = (param1String, param1SSLSession) -> true;
            builder.hostnameVerifier(hostnameVerifier);
            builder.connectTimeout(30, TimeUnit.SECONDS);
            builder.readTimeout(30, TimeUnit.SECONDS);
            builder.writeTimeout(30, TimeUnit.SECONDS);
            builder.addInterceptor(new ChuckInterceptor(App.getContext()));
            builder.addInterceptor(interceptor);
            return builder.build();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
