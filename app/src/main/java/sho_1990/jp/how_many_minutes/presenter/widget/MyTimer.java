package sho_1990.jp.how_many_minutes.presenter.widget;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import sho_1990.jp.how_many_minutes.presenter.service.TimerService;

/**
 *
 * ストップウォッチ表示用BroadcastReceiver
 * 実際のタイマー機能はTimerServiceクラスで行う。
 *
 * Created on 2016/08/01.
 */

public class MyTimer extends BroadcastReceiver {

    public interface MyTimerListener {
        /** 現在の経過時間を返す。 */
        void onTextView(String time);
    }


    private Context activity;
    private MyTimerListener listener;

    public String time;

    private MyTimer(Context activity, MyTimerListener listener) {
        this.activity = activity;
        this.listener = listener;
    }

    public static MyTimer newMyTimer(@NonNull Context activity, @Nullable MyTimerListener listener) {
        return new MyTimer(activity, listener);
    }

    /**
     * start()の前に呼び出す
     *
     * @return this
     *
     * */
    public MyTimer set() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(TimerService.ACTION);
        activity.registerReceiver(this, filter);

        return this;

    }

    /** viewの更新を止める。内部的にタイマーは動き続けている */
    public void pauseView() {
        activity.unregisterReceiver(this);
    }

    /**
     * タイマーを開始する。set()の後に呼び出す
     *
     * @return 自分自身
     * */
    public String start() {

        if (!isServiceRunning(activity, TimerService.class)) {

            Log.d("SERVICE NOT STARTED", "TimerService");

            Intent i = new Intent(activity, TimerService.class);
            activity.startService(i);
        }

        return time;
    }

    /**
     * タイマーを止める。
     *
     * @return 時刻(形式 00:00.0)
     * */
    public String stop() {

        Intent i = new Intent(activity, TimerService.class);
        activity.stopService(i);

        PreferenceManager
                .getDefaultSharedPreferences(activity.getApplicationContext())
                .edit()
                .putInt(TimerService.TIMER_COUNT, 0)
                .apply();

        String time = this.time;

        this.time = null;

        return time;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        time = intent.getStringExtra("time");

        Log.d("onRECEIVE", time);

        listener.onTextView(time);
    }

    /** Serviceの稼働状態をチェックする
     *
     * @return 稼働状態(true 稼働中
     *                   false 停止中)
     * */
    private static boolean isServiceRunning(Context c, Class<?> cls) {
        ActivityManager am = (ActivityManager) c.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningService = am.getRunningServices(Integer.MAX_VALUE);
        for (ActivityManager.RunningServiceInfo i : runningService) {
            if (cls.getName().equals(i.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
