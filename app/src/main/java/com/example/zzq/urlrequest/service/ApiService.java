package com.example.zzq.urlrequest.service;

import com.example.zzq.urlrequest.reqbody.NewsBody;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @auther tangedegushi
 * @creat 2018/8/15
 * @Decribe
 */
public interface ApiService {

    @POST("getNewsListInfo")
    Observable<String> getNews(@Body NewsBody body);

}
