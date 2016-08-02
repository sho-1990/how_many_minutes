package sho_1990.jp.how_many_minutes.widget;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.widget.TextView;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * ストップウォッチ表示用Widget
 *
 * Created on 2016/08/01.
 */

public class MyTimer {

    private TextView timeText;
    private Timer timer;
    private Handler mHandler = new Handler();

    private MyTimer(TextView timeText) {
        this.timeText = timeText;
    }

    public static MyTimer newMyTimer(@NonNull TextView textView) {
        // todo Stringリソースから持ってくる
        textView.setText("00:00.0");
        return new MyTimer(textView);
    }

    /**
     * @param delay 開始
     * @param period 終了
     * @return 自分自身
     * */
    public MyTimer start(long delay, long period) {
        if (delay > period) {
            throw new IllegalArgumentException("must delay < period");
        } else if (period < 0) {
            throw new IllegalArgumentException("must period > 0");
        } else if (delay < -1) {
            throw new IllegalArgumentException("must delay > -1 , period > 0");
        }

        timer = new Timer();
        CountUpTimerTask timerTask = new CountUpTimerTask(0);
        timer.schedule(timerTask, delay, period);

        return this;
    }

    public CharSequence stop() {
        if (timer == null) {
            throw new IllegalAccessError("must before create");
        }
        timer.cancel();
        CharSequence time = timeText.getText();
        timer = null;
        timeText.setText("00:00.0");
        return time;
    }

    private class CountUpTimerTask extends TimerTask {

        private int count;

        private CountUpTimerTask(int count) {
            this.count = count;
        }
        @Override
        public void run() {
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    count++;
                    long mm = count * 100 / 1000 / 60;
                    long ss = count * 100 / 1000 % 60;
                    long ms = (count * 100 - ss * 1000 - mm * 1000 * 60)/100;
                    // 桁数を合わせるために02d(2桁)を設定
                    timeText.setText(String.format(Locale.JAPANESE, "%1$02d:%2$02d.%3$01d", mm, ss, ms));
                }
            });
        }
    }
}
