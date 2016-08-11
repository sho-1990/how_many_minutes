package sho_1990.jp.how_many_minutes.presenter.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * タイマーを動作するサービスクラス
 */

public class TimerService extends Service {

    private Timer timer;
    private int count;

    private SharedPreferences mSharedPreferences;

    public static final String ACTION = "TimerService";
    public static final String TIMER_COUNT = "count";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        int delay = 0;
        int period = 100;

        mSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());

        count = mSharedPreferences.getInt(TIMER_COUNT, 0);

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                count++;
                long mm = count * 100 / 1000 / 60;
                long ss = count * 100 / 1000 % 60;
                long ms = (count * 100 - ss * 1000 - mm * 1000 * 60)/100;
                // 桁数を合わせるために02d(2桁)を設定
                Intent i = new Intent(ACTION);
                i.putExtra("time", String.format(Locale.JAPANESE, "%1$02d:%2$02d.%3$01d", mm, ss, ms));
                Log.d("TimerService", i.getStringExtra("time"));

                mSharedPreferences
                        .edit()
                        .putInt(TIMER_COUNT, count)
                        .apply();

                sendBroadcast(i);
            }
        }, delay, period);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
        timer = null;
        Log.d("SERVICE STOP", this.getClass().getName());
    }
}
