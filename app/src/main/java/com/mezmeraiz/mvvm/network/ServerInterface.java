package com.mezmeraiz.mvvm.network;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

public interface ServerInterface {

    @GET("/users")
    Observable<ResponseBody> users();

}
