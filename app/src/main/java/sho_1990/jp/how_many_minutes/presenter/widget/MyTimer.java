package sho_1990.jp.how_many_minutes.presenter.widget;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

/**
 *
 * ストップウォッチ表示用Widget
 *
 * Created on 2016/08/01.
 */
public class MyTimer {

    private Timer timer;

    public interface MyTimerListener {
        /** 現在の経過時間を返す。 */
        void onTextView(String time);
    }

    private static final String TIMER_STOP = "timer_stop";

    private Activity activity;
    private MyTimerListener listener;
    private SharedPreferences mSharedPreferences;

    private long count = 0L;


    private MyTimer(Activity activity, MyTimerListener listener) {
        this.activity = activity;
        this.listener = listener;
    }

    public static MyTimer newMyTimer(@NonNull Activity activity, @Nullable MyTimerListener listener) {
        return new MyTimer(activity, listener);
    }

    /**
     * Timerのカウンターをセットする
     * */
    public void set() {
        setCount();
        if (count != 0L) {
           start(0L, 100L);
        }
        listener.onTextView(createTime(count));
    }

    /**
     * タイマーを開始する。
     *
     * */
    public void start(long delay, long period) {

        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                count++;
                listener.onTextView(createTime(count));
            }
        }, delay, period);
    }

    public void restart() {
        // 初期化
        count = 0;
        long currentDate = new Date().getTime();
        if (mSharedPreferences == null) {
            mSharedPreferences = getDefaultSharedPreferences(activity.getApplicationContext());
        }
        count = currentDate - mSharedPreferences.getLong(TIMER_STOP, currentDate);
        start(0L, 100L);
    }

    public void pause() {
        mSharedPreferences
                .edit()
                .putLong(TIMER_STOP, (new Date()).getTime())
                .apply();
        timer.cancel();
        timer = null;
    }

    /**
     * タイマーを止める。
     *
     * @return 時刻(形式 00:00.0)
     * */
    public long stop() {
        timer.cancel();
        timer = null;
        long count = this.count;
        getDefaultSharedPreferences(activity.getApplicationContext())
                .edit()
                .remove(TIMER_STOP)
                .apply();
        this.count = 0;
        return count;
    }

    private String createTime(long count) {
        long mm = count * 100 / 1000 / 60;
        long ss = count * 100 / 1000 % 60;
        long ms = (count * 100 - ss * 1000 - mm * 1000 * 60)/100;
        return String.format(Locale.JAPANESE, "%1$02d:%2$02d.%3$01d", mm, ss, ms);
    }

    private void setCount() {
        long currentDate = (new Date()).getTime();
        if (mSharedPreferences == null) {
            mSharedPreferences = getDefaultSharedPreferences(activity.getApplicationContext());
        }
        count = currentDate - mSharedPreferences.getLong(TIMER_STOP, currentDate);
    }
}
