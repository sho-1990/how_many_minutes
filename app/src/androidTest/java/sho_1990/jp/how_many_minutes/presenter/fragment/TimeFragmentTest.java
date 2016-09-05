package sho_1990.jp.how_many_minutes.presenter.fragment;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import sho_1990.jp.how_many_minutes.R;
import sho_1990.jp.how_many_minutes.presenter.TimeActivity;

/**
 * Created on 2016/09/05.
 */
@RunWith(AndroidJUnit4.class)
public class TimeFragmentTest {

    @Rule
    public ActivityTestRule<TimeActivity> timeActivityActivityTestRule =
            new ActivityTestRule<>(TimeActivity.class);

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void clickTimerButton() {
        Espresso.onView(ViewMatchers.withId(R.id.imageButton)).perform(ViewActions.click());
    }

}