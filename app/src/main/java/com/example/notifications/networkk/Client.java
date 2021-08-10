package com.example.notifications.networkk;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Client {
//    private static Retrofit retrofit=null;
//
//    public static Retrofit getClient(String url)
//    {
//        if(retrofit==null)
//        {
//            retrofit=new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
//        }
//        return  retrofit;
//    }


    private static Retrofit retrofit = null;
    Retrofit retrofit2 = null;
    static Context mycontext ;
    public static final String BASE_URL = "https://europa-uat.paynext.co.in/";
    public static APIService getApiClient (Context context) {
        mycontext = context ;
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new BasicAuthInterceptor("shivani", "demo"))
//                .build();

        OkHttpClient okHttpClient1 = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain)
                            throws IOException {
                        Request request = chain.request();
                        if (!isNetworkAvailable()) {
                            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale \
                            request = request
                                    .newBuilder()
                                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                    .build();
                        }
                        return chain.proceed(request);
                    }
                })
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl("https://europa-uat.paynext.co.in/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient1)
                .build();
        APIService apiInterface = retrofit2.create(APIService.class);
        return apiInterface;
    }

    private static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mycontext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
//    public static Retrofit getClient() {
////
////        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
////        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//////        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//////                .addInterceptor(new BasicAuthInterceptor("shivani", "demo"))
//////                .build();
////        retrofit = new Retrofit.Builder()
////                .baseUrl(BASE_URL)
////                .addConverterFactory(GsonConverterFactory.create())
////                .client(okHttpClient).build();
////
////        return retrofit;
////
////    }
//
//    }
//    public static APIService getService() {
//
//        return getClient().create(APIService.class);
//
//    }
}
