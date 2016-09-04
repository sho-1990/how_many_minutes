package sho_1990.jp.how_many_minutes.presenter.widget;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;

import sho_1990.jp.how_many_minutes.presenter.TimeActivity;

/**
 * Created on 2016/09/03.
 */
@RunWith(AndroidJUnit4.class)
public class MyTimerTest {

    private MyTimer mMyTimer;
    private MyTimer mMyTimerNonListener;
    private Activity mActivity;

    private SharedPreferences mSharedPreferences;

    @Rule
    public ActivityTestRule<TimeActivity> mActivityRule =
            new ActivityTestRule<>(TimeActivity.class);
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @DataPoints
    public static long[] LONG_PARAMS = {
            0L,
            -1L,
            99L,
            100L,
            101L
    };

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityRule.getActivity();
        mMyTimer = MyTimer.newMyTimer(mActivity, new MyTimer.MyTimerListener() {
            @Override
            public void onTextView(String time) {
                // System.out.println(time);
            }
        });
        mMyTimerNonListener = MyTimer.newMyTimer(mActivity, null);

        mSharedPreferences = mActivity.getSharedPreferences("test", Context.MODE_PRIVATE);
    }

    @Test
    public void newMyTimer() throws Exception {
        // MyTimerのインスタンスを生成するのみなのでテストしていません。
        // setUpでやっているとも言えるかもしれません。

    }

    /**
     * setメソッドテスト。count = 0(最初の呼び出し)時に
     * setメソッド呼び出しを行ってもcountの加算が行われず0のままであることを
     * 確認する。
     * */
    @Test
    public void set_count_0() throws Exception {
        Field count = MyTimer.class.getDeclaredField( "count" );
        count.setAccessible(true);
        long countL = (long) count.get(mMyTimer);
        Assert.assertEquals(0L, countL);
        mMyTimer.set();
        Assert.assertEquals(0L, countL);
    }

    /**
     * setメソッドテスト。count = 0以外（最初以外の呼び出し）時に
     * setメソッド呼び出し後からcountが加算され始めることを確認する。
     * */
    @Test
    public void set_count_not_0() throws Exception {
        Field count = MyTimer.class.getDeclaredField( "count" );
        count.setAccessible(true);
        count.set(mMyTimer, 1L);
        mMyTimer.set();
        System.out.println(count.get(mMyTimer));
        Assert.assertNotEquals(1L, (long) count.get(mMyTimer));
        Thread.sleep(20);
//        mMyTimer.stop();
    }

    @Theory
    public void start(long delay, long period) throws Exception {
        MyTimer myTimer = MyTimer.newMyTimer(mActivity, null);
        thrown.expect(IllegalArgumentException.class);
        myTimer.start(delay, period);
        myTimer.stop();
    }

    @Test
    public void pause() throws Exception {

    }

    @Test
    public void stop() throws Exception {

    }

}