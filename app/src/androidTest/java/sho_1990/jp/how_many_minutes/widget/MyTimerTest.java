package sho_1990.jp.how_many_minutes.widget;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import sho_1990.jp.how_many_minutes.presenter.widget.MyTimer;

/**
 * Created on 2016/08/01.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class MyTimerTest {

    private MyTimer timer;

    @Before
    public void setUp() {
        timer = MyTimer.
                    newMyTimer(new TextView(InstrumentationRegistry.getContext()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createThrows() {
        timer.start(100L, 10L);
        timer.start(-10L, 10L);
        timer.start(10L, -10L);
        timer.start(0L, 0L);
        timer.start(100L, 0L);
        timer.start(0L, 100L);
    }

    @Test
    public void createObj() {
        Assert.assertEquals(MyTimer.
                                newMyTimer(
                                        new TextView(InstrumentationRegistry.getContext())),
                                                     timer.start(10, 100));
    }

    @Test(expected = IllegalAccessError.class)
    public void stopThrows() {
        MyTimer.newMyTimer(new TextView(InstrumentationRegistry.getContext())).stop();
    }

}