package com.example.trevorbernard.parkhere;

import android.support.test.rule.ActivityTestRule;

import com.example.trevorbernard.parkhere.Client.LoginActivity;
import com.example.trevorbernard.parkhere.Client.RegisterActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by zilongxiao on 11/7/16.
 * This is to test that we will only register users if the password contains a special character
 * or is longer than 10 digits
 */

public class PasswordSecurityTest {
    @Rule
    public ActivityTestRule<RegisterActivity> registerActivityRule = new ActivityTestRule<RegisterActivity>(
            RegisterActivity.class,false,false);

    //Password Successfully Edits
    @Test
    public void registerPasswordCheck() {
        onView(withId(R.id.EditTextPassword))
                .perform(typeText("myPassword"),closeSoftKeyboard());
        onView(withId(R.id.RegisterButton)).perform(click());
        onView(withId(R.id.EditTextPassword)).check(matches(withText("myPassword")));
    }

    //Password Confirm Successfully Edits
    @Test
    public void registerPasswordConfirmCheck() {
        onView(withId(R.id.EditTextConfirmPassword))
                .perform(typeText("myConfirmPassword"),closeSoftKeyboard());
        onView(withId(R.id.RegisterButton)).perform(click());
        onView(withId(R.id.EditTextConfirmPassword)).check(matches(withText("myConfirmPassword")));
    }

    //Check If the password is successful
    @Test
    public void PasswordFailureCheck() {
        onView(withId(R.id.EditTextPassword))
                .perform(typeText("invalidP"),closeSoftKeyboard());
        onView(withId(R.id.RegisterButton)).perform(click());
        onView(withId(R.id.EditTextPassword)).check(matches(isDisplayed()));
    }
}
