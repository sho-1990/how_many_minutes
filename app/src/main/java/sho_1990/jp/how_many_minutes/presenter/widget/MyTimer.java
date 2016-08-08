package sho_1990.jp.how_many_minutes.presenter.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.widget.TextView;

import sho_1990.jp.how_many_minutes.R;
import sho_1990.jp.how_many_minutes.presenter.service.TimerService;

/**
 *
 * ストップウォッチ表示用Widget
 *
 * Created on 2016/08/01.
 */

public class MyTimer extends BroadcastReceiver {

    private Context activity;
    private TextView timeText;

    private MyTimer(@NonNull Context activity, @NonNull TextView timeText) {
        this.activity = activity;
        this.timeText = timeText;
    }

    public static MyTimer newMyTimer(@NonNull Context activity, @NonNull TextView textView) {
        // todo Stringリソースから持ってくる
        textView.setText(activity.getString(R.string.init_time));
        return new MyTimer(activity, textView);
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

        IntentFilter filter = new IntentFilter();
        filter.addAction(TimerService.ACTION);
        activity.registerReceiver(this, filter);

        Intent i = new Intent(activity, TimerService.class);
        i.putExtra("delay", delay);
        i.putExtra("period", period);
        activity.startService(i);

        return this;
    }

    public CharSequence stop() {

        CharSequence time = timeText.getText();
        timeText.setText(activity.getString(R.string.init_time));

        Intent i = new Intent(activity, TimerService.class);
        activity.stopService(i);
        activity.unregisterReceiver(this);

        return time;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String time = intent.getStringExtra("time");
        timeText.setText(time);
    }

}
