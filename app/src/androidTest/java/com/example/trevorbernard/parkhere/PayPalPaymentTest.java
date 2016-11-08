package com.example.trevorbernard.parkhere;

import android.app.ActivityManager;
import android.content.Intent;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.braintreepayments.cardform.view.CardForm;
import com.example.trevorbernard.parkhere.Client.RentSpotActivity;

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
import static org.hamcrest.Matchers.containsString;

/**
 * Created by trevorbernard on 11/6/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class PayPalPaymentTest {
    //test credit card number of valid visa for Braintree sandbox mode
    public static final String TEST_CREDIT_CARD_NUMBER = "4111111111111111";
    //valid expiration date
    public static final String TEST_EXP_DATE = "11/19";

    @Rule
    public ActivityTestRule<RentSpotActivity> mActivityRule = new ActivityTestRule<>(
            RentSpotActivity.class, false, false);

    @Test
    public void creditCardTest() {
        // from firebase
        String spot_UID = "-KVNiLHGRbblnHB2zlXk";
        // random distance value
        Double distance = 2.1;
        Intent i = new Intent();
        i.putExtra("spotUID", spot_UID);
        i.putExtra("distance", distance);
        mActivityRule.launchActivity(i);
        System.out.println("asdf");
        onView(withId(R.id.RentButton)).perform(click());

        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // now in BrainTree payment activity
        onView(withId(com.braintreepayments.api.dropin.R.id.bt_card_form)).check(matches(isDisplayed()));


    }

    @Test
    public void payPalTest() {
        // from firebase
        String spot_UID = "-KVNiLHGRbblnHB2zlXk";
        // random distance value
        Double distance = 2.1;
        Intent i = new Intent();
        i.putExtra("spotUID", spot_UID);
        i.putExtra("distance", distance);
        mActivityRule.launchActivity(i);
        System.out.println("asdf");
        onView(withId(R.id.RentButton)).perform(click());

        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // check for paypal button
        onView(withText(containsString("PayPal"))).check(matches(isDisplayed()));


    }
}
