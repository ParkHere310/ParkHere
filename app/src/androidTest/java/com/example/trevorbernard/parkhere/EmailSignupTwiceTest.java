package com.example.trevorbernard.parkhere;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import com.example.trevorbernard.parkhere.Client.RegisterActivity;
import com.example.trevorbernard.parkhere.User.User;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by zilongxiao on 11/7/16.
 * This is to test whether one user can sign up with the same email
 */

public class EmailSignupTwiceTest {

    @Rule
    public ActivityTestRule<RegisterActivity> mRegisterActivityRule = new ActivityTestRule<>(
            RegisterActivity.class,false,false);

    @Test
    public void TestEmailSignup() {
        Intent i = new Intent();
        mRegisterActivityRule.launchActivity(i);
        onView(withId(R.id.EditTextEmail))
                .perform(typeText("test43@gmail.com"),closeSoftKeyboard());
        onView(withId(R.id.RegisterButton)).perform(click());
        onView(withId(R.id.EditTextEmail)).check(matches(withText("test43@gmail.com")));
        String duplicateEmail = "test43@gmail.com";

        User mUser = new User();
        mUser.setEmail(duplicateEmail);
         onView(withId(R.id.EditTextEmail)).check(matches(withText(mUser.getEmail())));
    }
}
