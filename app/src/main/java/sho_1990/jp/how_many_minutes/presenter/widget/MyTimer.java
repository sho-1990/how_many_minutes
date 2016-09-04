package sho_1990.jp.how_many_minutes.presenter.widget;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

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

    public interface MyTimerListener {
        /** 現在の経過時間を返す。 */
        void onTextView(String time);
    }

    private Timer timer;

    private static final String TIMER_STOP = "timer_stop";

    private MyTimerListener listener;
    private SharedPreferences mSharedPreferences;

    private long count;

    private MyTimer(Activity activity, MyTimerListener listener) {
        this.listener = listener;
        this.count = 0L;
        mSharedPreferences = getDefaultSharedPreferences(activity.getApplicationContext());
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
            return;
        }
        listener.onTextView(createTime(count));
    }

    /**
     * タイマーを開始する。
     *
     * */
    public void start(long delay, long period) {
        if (delay != 0 || period != 100) {
            throw new IllegalArgumentException("設定値が無効です。");
        }

        if (count == 0L) {
            mSharedPreferences
                    .edit()
                    .putLong(TIMER_STOP, (new Date()).getTime())
                    .apply();
        }

        if (timer != null) {
            timer.cancel();
        }

        timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Log.d("start_count", String.valueOf(count));
                count++;
                listener.onTextView(createTime(count));
            }
        }, delay, period);
    }

    public void pause() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * タイマーを止める。
     *
     * @return 時刻(形式 00:00.0)
     * */
    public long stop() {
        long count;
        count = this.count;
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        mSharedPreferences
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
        long currentDate = new Date().getTime();
        count = (currentDate - mSharedPreferences.getLong(TIMER_STOP, currentDate)) / 100;
    }
}
