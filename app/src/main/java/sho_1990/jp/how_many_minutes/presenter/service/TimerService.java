package sho_1990.jp.how_many_minutes.presenter.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * タイマーを動作するサービスクラス
 */

public class TimerService extends Service {

    private Timer timer;
    private int count;
    public static final String ACTION = "TimerService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        int delay = 0;
        int period = 100;

        if (intent != null) {
            delay = intent.getIntExtra("delay", 0);
            period = intent.getIntExtra("period", 100);
        }

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                count++;
                long mm = count * 100 / 1000 / 60;
                long ss = count * 100 / 1000 % 60;
                long ms = (count * 100 - ss * 1000 - mm * 1000 * 60)/100;
                // 桁数を合わせるために02d(2桁)を設定
                Intent i = new Intent(ACTION);
                i.putExtra("time", String.format(Locale.JAPANESE, "%1$02d:%2$02d.%3$01d", mm, ss, ms));
                sendBroadcast(i);
            }
        }, delay, period);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer = null;
    }
}
