package com.example.trevorbernard.parkhere;

/**
 * Created by trevorbernard on 11/7/16.
 */


import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.trevorbernard.parkhere.Client.RentSpotActivity;
import com.example.trevorbernard.parkhere.Client.SearchResultActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by trevorbernard on 11/6/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class FilterTest {
    @Rule
    public ActivityTestRule<SearchResultActivity> mActivityRule = new ActivityTestRule<>(
            SearchResultActivity.class,false,false);

    @Test
    public void filterTest() {

        Intent i = new Intent();
        i.putExtra("address", "2339 Bolsover 77005");
        i.putExtra("startDateLong", 1234);
        i.putExtra("endDateLong", 12345);
        mActivityRule.launchActivity(i);


        onView(withId(R.id.filter_button)).perform(click());
        onView(withId(R.id.sortBySpotRating)).check(matches(isDisplayed()));

    }


}
