package com.example.zzq.urlrequest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.zzq.urlrequest.service.HotKeyModel;
import com.zzq.netlib.mvp.base.MvpBaseActivity;
import com.zzq.netlib.utils.Logger;
import com.zzq.netlib.view.LoadingView;
import com.zzq.netlib.view.dialog.EasyDialog;
import com.zzq.netlib.view.dialog.ViewHolder;
import com.zzq.netlib.view.dialog.ViewListener;

public class MainActivity extends MvpBaseActivity<HotKeyPresenter> {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LoadingView loadingView = findViewById(R.id.loadingView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                EasyDialog dialog = new EasyDialog.Builder().setLayoutId(R.layout.netlib_dialog_loading)
                        .build();
                dialog.show(getSupportFragmentManager());
//                loadingView.start();
            }
        });

    }

    @NonNull
    @Override
    public HotKeyPresenter onCreatePresenter() {
        return new HotKeyPresenter(new HotKeyModel(),this);
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
