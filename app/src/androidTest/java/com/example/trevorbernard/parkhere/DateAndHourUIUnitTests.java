package com.example.trevorbernard.parkhere;

import android.support.test.espresso.contrib.PickerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.trevorbernard.parkhere.Client.PostSpotActivity;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * Created by junseob on 11/6/16.
 */

@RunWith(AndroidJUnit4.class)

public class DateAndHourUIUnitTests {

    public static final String STRING_TO_BE_TYPED = "Espresso";


    @Rule
    public ActivityTestRule<PostSpotActivity> mActivityRule = new ActivityTestRule<>(
            PostSpotActivity.class);

    @Test
    public void testStartDate() {
        int year = 2016;
        int month = 11;
        int day = 15;

        onView(withId(R.id.startDatePicker.getId())).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year, month + 1, day));



        //onView(withId(R.id.postButton)).perform(click());

        DatePicker startDateP = R.id.startDatePicker;
        onView(withText(startDateP.getYear() + "/" + startDateP.getMonth() + "/" + startDateP.getDayOfMonth())).check(matches(withText(year + "/" + month + "/" + day)));
    }

    @Test
    public void testEndDate() {
        int year = 2016;
        int month = 11;
        int day = 15;

        onView(withId(R.id.endDatePicker.getId())).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year, month + 1, day));
        //onView(withId(R.id.postButton)).perform(click());

        DatePicker endDateP = R.id.endDatePicker;
        onView(withText(endDateP.getYear() + "/" + endDateP.getMonth() + "/" + endDateP.getDayOfMonth())).check(matches(withText(year + "/" + month + "/" + day)));
    }

    @Test
    public void testStartTime() {
        int hour = 9;
        int minutes = 10;

        onView(withId(R.id.startTime_picker.getId())).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setTime(hour, minutes));
        //onView(withId(R.id.postButton)).perform(click());

        TimePicker endDateP = R.id.startTime_picker;

        onView(withText(R.id.startTime_picker.getHour())).check(matches(withText(hour)));
    }

    @Test
    public void testEndTime() {
        int hour = 10;
        int minutes = 15;

        onView(withId(R.id.endTime_picker.getId())).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setTime(hour, minutes));
        //onView(withId(R.id.postButton)).perform(click());

       // TimePicker endDateP = R.id.endTime_picker;
        onView(withText(R.id.endTime_picker.getHour())).check(matches(withText(hour)));
        //onView(withText(endHour).check(matches(withText(hour + ":" + minutes)));
    }


}