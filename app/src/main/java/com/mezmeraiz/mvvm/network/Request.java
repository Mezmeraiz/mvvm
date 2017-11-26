package com.mezmeraiz.mvvm.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.mezmeraiz.mvvm.etc.Consts;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Request {

    public Observable<ResponseBody> users() {
        return getInterface(Consts.BASE_URL, null).users();
    }

    private ServerInterface getInterface(String baseUrl, OkHttpClient client){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        if (client != null)
            builder.client(client);
        return builder
                .build()
                .create(ServerInterface.class);
    }

}
