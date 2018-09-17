package com.zzq.netlib.view.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.zzq.netlib.R;
import com.zzq.netlib.utils.Logger;

/**
 * @auther tangedegushi
 * @creat 2018/9/12
 * @Decribe
 */
public class EasyDialog extends DialogFragment {

    private static final String LAYOUT = "layout_id";
    private static final String DIM = "dim_amount";
    private static final String GRAVITY = "gravity";
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String MARGIN = "margin";
    private static final String LISTENER = "listener";

    private int layoutId;
    private float dimAmount = 0.5f;
    private int gravity = Gravity.CENTER;
    private int width;
    private int height;
    private int margin;
    private ViewListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BaseDialog);

        if (savedInstanceState != null) {
            layoutId = savedInstanceState.getInt(LAYOUT);
            dimAmount = savedInstanceState.getFloat(DIM);
            gravity = savedInstanceState.getInt(GRAVITY);
            width = savedInstanceState.getInt(WIDTH);
            height = savedInstanceState.getInt(HEIGHT);
            margin = savedInstanceState.getInt(MARGIN);
            listener = savedInstanceState.getParcelable(LISTENER);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(LAYOUT, layoutId);
        outState.putFloat(DIM, dimAmount);
        outState.putInt(GRAVITY, gravity);
        outState.putInt(WIDTH, width);
        outState.putInt(HEIGHT, height);
        outState.putInt(MARGIN, margin);
        outState.putParcelable(LISTENER, listener);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(layoutId, container, false);
        if (listener != null) {
            listener.convert(ViewHolder.create(mView), this);
        }
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initWindowParams();
    }

    private void initWindowParams() {
        Window window = getDialog().getWindow();
        if (window == null) return;
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.dimAmount = dimAmount;
        if (gravity != Gravity.CENTER) {
            lp.gravity = gravity;
            if (gravity == Gravity.BOTTOM) {
                lp.windowAnimations = R.style.BaseDialog_Bottom_Anim;
            } else if (gravity == Gravity.TOP) {
                lp.windowAnimations = R.style.BaseDialog_top_Anim;
            }
        }
        if (margin != 0) {
            int marginTotal = 2 * (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, margin, getResources().getDisplayMetrics());
            int screenWidth = getResources().getDisplayMetrics().widthPixels;
            lp.width = screenWidth - marginTotal;
        } else {
            if (width != 0) {
                lp.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, getResources().getDisplayMetrics());
            }
        }
        if (height != 0) {
            lp.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, getResources().getDisplayMetrics());
        }
        window.setAttributes(lp);
    }

    public void show(FragmentManager manager){
        show(manager,"loading");
    }

    private void initBuild(Builder builder) {
        layoutId = builder.layoutId;
        dimAmount = builder.dimAmount;
        gravity = builder.gravity;
        width = builder.width;
        height = builder.height;
        margin = builder.margin;
        listener = builder.listener;
        setCancelable(builder.outCancelable);
    }

    public static class Builder {
        private int layoutId;
        private float dimAmount = 0.5f;
        private int gravity = Gravity.CENTER;
        private int width;
        private int height;
        private int margin;
        private boolean outCancelable = true;
        private ViewListener listener;
        private boolean hasLayoutId = false;

        public Builder setLayoutId(int layoutId) {
            hasLayoutId = true;
            this.layoutId = layoutId;
            return this;
        }

        public Builder setOutCancelable(boolean outCancelable) {
            this.outCancelable = outCancelable;
            return this;
        }

        public Builder setDimAmount(float dimAmount) {
            this.dimAmount = dimAmount;
            return this;
        }

        public Builder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setMargin(int margin) {
            this.margin = margin;
            return this;
        }

        public Builder setViewLisenter(ViewListener listener) {
            this.listener = listener;
            return this;
        }

        public EasyDialog build() {
            if (!hasLayoutId) {
                throw new RuntimeException("you must call setLayoutId() method");
            }
            EasyDialog dialog = new EasyDialog();
            dialog.initBuild(this);
            return dialog;
        }

        public EasyDialog show(FragmentManager manager){
            if (!hasLayoutId) {
                throw new RuntimeException("you must call setLayoutId() method");
            }
            EasyDialog dialog = new EasyDialog();
            dialog.initBuild(this);
            dialog.show(manager,"loading");
            return dialog;
        }
    }
}
