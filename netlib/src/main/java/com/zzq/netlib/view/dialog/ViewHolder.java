package com.zzq.netlib.view.dialog;

import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.TextView;

/**
 * @auther tangedegushi
 * @creat 2018/9/12
 * @Decribe
 */
public class ViewHolder {

    private View convertView;

    private ViewHolder(View mView) {
        this.convertView = mView;
    }

    public static ViewHolder create(View view) {
        return new ViewHolder(view);
    }

    public <T extends View> T getView(@IdRes int viewId) {
        View view = convertView.findViewById(viewId);
        return (T) view;
    }

    /**
     * Will set the text of a TextView.
     *
     * @param viewId The view id.
     * @param value  The text to put in the text view.
     * @return The BaseViewHolder for chaining.
     */
    public ViewHolder setText(@IdRes int viewId, CharSequence value) {
        TextView textView = getView(viewId);
        textView.setText(value);
        return this;
    }

    public ViewHolder setText(@IdRes int idRes, @StringRes int strId) {
        TextView textView = getView(idRes);
        textView.setText(strId);
        return this;
    }

    /**
     * Sets the on click listener of the view.
     *
     * @param viewId   The view id.
     * @param listener The on click listener;
     * @return The BaseViewHolder for chaining.
     */
    public ViewHolder setOnClickListener(@IdRes int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }
}
