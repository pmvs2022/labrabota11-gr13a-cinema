package com.ivanova.cinema.LoginView;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.ivanova.cinema.R;
import com.ivanova.cinema.View.LoginView.Login;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginTest {

    @Rule
    public ActivityScenarioRule<Login> rule = new ActivityScenarioRule<Login>(Login.class);

    @Test
    public void loginTest() {
        onView(withId(R.id.btn_register)).perform(click());
        onView(withId(R.id.btn_register_reg)).check(matches(isDisplayed()));

        onView(withId(R.id.btn_enter_reg)).perform(click());
        onView(withId(R.id.log_login)).perform(typeText("daria@gmail.com"));
        onView(withId(R.id.log_password)).perform(typeText("d_pswd_123"));

        onView(withId(R.id.btn_enter)).perform(click());
        onView(withId(R.id.cinemaListRecyclerView)).check(matches(isDisplayed()));

        onView(withId(R.id.cinemaListRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, customAction()));
        onView(withId(R.id.tv_cinemaName)).check(matches(withText("Космос")));
    }

    ViewAction customAction() {
        return new ViewAction() {
            @Override
            public String getDescription() {
                return null;
            }

            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public void perform(UiController uiController, View view) {
                view.performClick();
            }
        };

    }
}