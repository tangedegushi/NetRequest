package com.zzq.netlib.di;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.app.Service;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.zzq.netlib.di.scope.AppScope;
import com.zzq.netlib.utils.Logger;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import javax.inject.Inject;

/**
 * @auther tangedegushi
 * @creat 2018/8/16
 * @Decribe
 */
@AppScope
public class ActivityManager implements Application.ActivityLifecycleCallbacks {

    private static final String IS_NOT_ADD_ACTIVITY_MANAGER = "is_not_add_activity_manager";
    private Stack<Activity> stack;
    private Activity mCurrentActivity;

    @Inject
    public Application app;

    @Inject
    public ActivityManager() {
        stack = new Stack<>();
    }

    @Inject
    public void register() {
        Logger.zzqLog().d("register ActivityManager to manager activity");
        app.registerActivityLifecycleCallbacks(this);
    }

    public void unRegister(Application app) {
        app.unregisterActivityLifecycleCallbacks(this);
    }


    //-------------------管理activity相关方法-----------------------------

    /**
     * 将在前台的 {@link Activity} 赋值给 {@code currentActivity}, 注意此方法是在 {@link Activity#onResume} 方法执行时将栈顶的 {@link Activity} 赋值给 {@code currentActivity}
     * 所以在栈顶的 {@link Activity} 执行 {@link Activity#onCreate} 方法时使用 {@link #getCurrentActivity()} 获取的就不是当前栈顶的 {@link Activity}, 可能是上一个 {@link Activity}
     * 如果在 App 启动第一个 {@link Activity} 执行 {@link Activity#onCreate} 方法时使用 {@link #getCurrentActivity()} 则会出现返回为 {@code null} 的情况
     * 想避免这种情况请使用 {@link #getTopActivity()}
     *
     * @param currentActivity
     */
    public void setCurrentActivity(Activity currentActivity) {
        this.mCurrentActivity = currentActivity;
    }

    /**
     * 获取在前台的 {@link Activity} (保证获取到的 {@link Activity} 正处于可见状态, 即未调用 {@link Activity#onStop()}), 获取的 {@link Activity} 存续时间
     * 是在 {@link Activity#onStop()} 之前, 所以如果当此 {@link Activity} 调用 {@link Activity#onStop()} 方法之后, 没有其他的 {@link Activity} 回到前台(用户返回桌面或者打开了其他 App 会出现此状况)
     * 这时调用 {@link #getCurrentActivity()} 有可能返回 {@code null}, 所以请注意使用场景和 {@link #getTopActivity()} 不一样
     * <p>
     * Example usage:
     * 使用场景比较适合, 只需要在可见状态的 {@link Activity} 上执行的操作
     * 如当后台 {@link Service} 执行某个任务时, 需要让前台 {@link Activity} ,做出某种响应操作或其他操作,如弹出 {@link Dialog}, 这时在 {@link Service} 中就可以使用 {@link #getCurrentActivity()}
     * 如果返回为 {@code null}, 说明没有前台 {@link Activity} (用户返回桌面或者打开了其他 App 会出现此状况), 则不做任何操作, 不为 {@code null}, 则弹出 {@link Dialog}
     *
     * @return
     */
    public Activity getCurrentActivity() {
        return mCurrentActivity != null ? mCurrentActivity : null;
    }

    /**
     * 获取最近启动的一个 {@link Activity}, 此方法不保证获取到的 {@link Activity} 正处于前台可见状态
     * 即使 App 进入后台或在这个 {@link Activity} 中打开一个之前已经存在的 {@link Activity}, 这时调用此方法
     * 还是会返回这个最近启动的 {@link Activity}, 因此基本不会出现 {@code null} 的情况
     * 比较适合大部分的使用场景, 如 startActivity
     * <p>
     * Tips: mActivityList 容器中的顺序仅仅是 Activity 的创建顺序, 并不能保证和 Activity 任务栈顺序一致
     *
     * @return
     */
    public Activity getTopActivity() {
        return stack.size() > 0 ? stack.peek() : null;
    }

    /**
     * @param activity 需要添加进栈管理的activity
     */
    public void addActivity(Activity activity) {
        stack.add(activity);
    }

    /**
     * @param activity 需要从栈管理中删除的activity
     * @return
     */
    public boolean removeActivity(Activity activity) {
        return stack.remove(activity);
    }

    /**
     * 返回一个存储所有未销毁的 {@link Activity} 的集合
     *
     * @return
     */
    public Stack<Activity> getActivityList() {
        return stack;
    }

    /**
     * @param activity 查询指定activity在栈中的位置，从栈顶开始
     * @return
     */
    public int searchActivity(Activity activity) {
        return stack.search(activity);
    }

    /**
     * @param activity 将指定类名的activity从栈中删除并finish()掉
     */
    public void finishActivityClass(Class<Activity> activity) {
        if (activity != null) {
            Iterator<Activity> iterator = stack.iterator();
            while (iterator.hasNext()) {
                Activity next = iterator.next();
                if (next.getClass().equals(activity)) {
                    iterator.remove();
                    next.finish();
                }
            }
        }
    }

    /**
     * 移除所有Activity
     */
    public void finishAllActivity() {
        Iterator<Activity> iterator = stack.iterator();
        while (iterator.hasNext()) {
            Activity next = iterator.next();
            iterator.remove();
            next.finish();
        }
    }

    /**
     * @param activityClass 指定类名的activity不移除
     */
    public void finishAllExcludeActivity(Class<Activity>... activityClass) {
        List<Class<Activity>> list = Arrays.asList(activityClass);
        Iterator<Activity> iterator = stack.iterator();
        while (iterator.hasNext()) {
            Activity next = iterator.next();
            if (list.contains(next.getClass()))
                continue;
            iterator.remove();
            next.finish();
        }
    }


    //----------------------------弹窗显示相关方法---------------------------
    public void showToat(@StringRes int resId) {
        showToast(app.getResources().getString(resId));
    }

    public void showToast(String message) {
        showToast(message, false);
    }

    public void showToast(String message, boolean isLong) {
        Toast.makeText(app, message, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }

    public void showSnackBar(String message, boolean isLong) {
        if (getCurrentActivity() == null) {
            Logger.zzqLog().w("currentActivity = null when show SnackBar");
        }
        View decorView = getCurrentActivity().getWindow().getDecorView();
        Snackbar.make(decorView, message, isLong ? Snackbar.LENGTH_LONG : Snackbar.LENGTH_SHORT).show();
    }


    // ------------------------------ activity life -----------------------------
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        //如果 intent 包含了此字段,并且为 true 说明不加入到 list 进行统一管理
        boolean isNotAdd = false;
        if (activity.getIntent() != null)
            isNotAdd = activity.getIntent().getBooleanExtra(ActivityManager.IS_NOT_ADD_ACTIVITY_MANAGER, false);

        if (!isNotAdd)
            addActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        setCurrentActivity(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (getCurrentActivity() == activity) {
            setCurrentActivity(null);
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        removeActivity(activity);
    }
}
