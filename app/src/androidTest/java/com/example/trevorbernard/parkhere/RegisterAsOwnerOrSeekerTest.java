package com.example.trevorbernard.parkhere;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import com.example.trevorbernard.parkhere.Client.RegisterActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by zilongxiao on 11/7/16.
 */

public class RegisterAsOwnerOrSeekerTest {
    @Rule
    public ActivityTestRule<RegisterActivity> mRegisterActivityRule = new ActivityTestRule<>(
            RegisterActivity.class,false,false);

    @Test
    public void RegisterAsOwnerOrSeeker() {
        Intent i = new Intent();
        mRegisterActivityRule.launchActivity(i);
        onView(withId(R.id.ownerCheckBox)).check(matches(isDisplayed()));
        onView(withId(R.id.seekerCheckBox)).check(matches(isDisplayed()));
    }
}
