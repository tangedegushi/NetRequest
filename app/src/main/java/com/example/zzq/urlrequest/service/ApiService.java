package com.example.zzq.urlrequest.service;

import com.example.zzq.urlrequest.bean.HotKey;
import com.example.zzq.urlrequest.bean.MainBrand;
import com.example.zzq.urlrequest.reqbody.NewsBody;
import com.zzq.netlib.mvp.response.BaseResponse;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @auther tangedegushi
 * @creat 2018/8/15
 * @Decribe
 */
public interface ApiService {

    @POST("getNewsListInfo")
    Observable<String> getNews(@Body NewsBody body);

    @GET("article/list/{index}/json")
    Observable<BaseResponse<MainBrand>> getMainBrand(@Path("index") int index);

    @GET("hotkey/json")
    Observable<BaseResponse<List<HotKey>>> getHotKey();

}
