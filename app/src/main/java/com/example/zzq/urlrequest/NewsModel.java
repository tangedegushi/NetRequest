package com.example.zzq.urlrequest;

import com.example.zzq.urlrequest.reqbody.NewsBody;
import com.example.zzq.urlrequest.service.ApiService;
import com.zzq.netlib.mvp.BaseModel;
import io.reactivex.Observable;

/**
 * @auther tangedegushi
 * @creat 2018/9/7
 * @Decribe
 */
public class NewsModel extends BaseModel<String> {
    @Override
    public Observable<String> loadCommonData() {
        return netManager.getRetrofitService(ApiService.class)
                .getNews(new NewsBody(1));
    }
}
