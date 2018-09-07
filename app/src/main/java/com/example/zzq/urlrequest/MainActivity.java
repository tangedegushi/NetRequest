package com.example.zzq.urlrequest;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.zzq.urlrequest.reqbody.NewsBody;
import com.example.zzq.urlrequest.service.ApiService;
import com.zzq.netlib.base.App;
import com.zzq.netlib.mvp.IPresenter;
import com.zzq.netlib.mvp.base.MvpBaseActivity;
import com.zzq.netlib.utils.Logger;
import com.zzq.netlib.utils.UtilApp;

import java.util.Stack;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends MvpBaseActivity<NewsPresenter> {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        int targetMemoryCacheSize = (int) (activityManager.getMemoryClass());
//        Logger.zzqLog().d("targetMemoryCacheSize = " + targetMemoryCacheSize);
//        ((App) getApplication()).getAppComponent().netManager().getRetrofitService(ApiService.class)
//                .getNews(new NewsBody(1))
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        Logger.zzqLog().d("--next--");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

    }

    @NonNull
    @Override
    public NewsPresenter onCreatePresenter() {
        return new NewsPresenter(new NewsModel(),this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        Logger.zzqLog().d(metrics.widthPixels+"    "+metrics.heightPixels+"   ");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoadFail(Exception e) {

    }

    @Override
    public <String>void onLoadSuccess(String msg) {
        Logger.zzqLog().d(msg);
    }

    @Override
    public <String>void onShowMessage(String t) {

    }
}
