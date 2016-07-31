package sho_1990.jp.how_many_minutes.presenter;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;

import layout.TimeFragment;
import sho_1990.jp.how_many_minutes.R;
import sho_1990.jp.how_many_minutes.databinding.ActivityTimeBinding;

/**
 * Created 2016/07/31.
 */

public class TimeActivity extends BaseActivity {

    private ActivityTimeBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_time);
        mBinding.tabhost
                .setup(this, getSupportFragmentManager(), R.id.container);
        mBinding.tabhost
                .addTab(mBinding
                        .tabhost
                        .newTabSpec("測定")
                        .setIndicator("",
                                      ResourcesCompat.getDrawable(getResources(),
                                      R.drawable.ic_alarm_on_black_24dp,
                                      null)
                        ),
                        TimeFragment.class,
                        null);
        mBinding.tabhost
                .addTab(mBinding.tabhost
                        .newTabSpec("履歴")
                        .setIndicator("",
                                      ResourcesCompat.getDrawable(getResources(),
                                      R.drawable.ic_assignment_black_24dp,
                                      null)
                        ),
                        // todo 後日変更する
                        TimeFragment.class,
                        null);

    }
}
