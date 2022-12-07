package edu.iastate.code42;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.PositionAssertions.isCompletelyBelow;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import android.content.Intent;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

/**
 * @author Andrew
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserTest {
    private static final int SIMULATED_DELAY_MS = 1000;

    @Rule
    public ActivityTestRule<UserViewActivity> userViewActivityRule = new ActivityTestRule<>(UserViewActivity.class);

    @BeforeClass
    public static void setup(){
        ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(MainActivity.class);
        mainActivityRule.launchActivity(new Intent());

        onView(withId(R.id.loginUsernameEntryField))
                .perform(typeText("teststudent3"), closeSoftKeyboard());
        onView(withId(R.id.loginPasswordEntryField))
                .perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.helloMessage)).check(matches(withText("Hello, Jane!")));
    }

    @AfterClass
    public static void destroy(){
        ActivityTestRule<DashboardActivity> d = new ActivityTestRule<>(DashboardActivity.class);
        d.launchActivity(new Intent());
        d.getActivity().logout();

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
    }

    @Test
    public void profile(){
        userViewActivityRule.launchActivity(new Intent());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.firstNameView)).check(matches(withText("Jane")));
        onView(withId(R.id.lastNameView)).check(matches(withText("Doe")));
        onView(withId(R.id.usernameView)).check(matches(withText("teststudent3")));
    }

    @Test
    public void profileEdit(){
        userViewActivityRule.launchActivity(new Intent());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.floatingEditUser)).perform(click());

        String t = userViewActivityRule.getActivity().userSession.getString("token", "").toString();

        onView(withId(R.id.emailView)).perform(replaceText(t), closeSoftKeyboard());
        onView(withId(R.id.floatingEditUser)).perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.emailView)).check(matches(withText(t)));
    }

    @Test
    public void profileEditPassword(){
        userViewActivityRule.launchActivity(new Intent());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.floatingEditUser)).perform(click());
        onView(withId(R.id.changePasswordButton)).perform(click());

        onView(withId(R.id.currentPassword)).perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.newPassword)).perform(typeText("helloWorld"), closeSoftKeyboard());
        onView(withId(R.id.confirmPassword)).perform(typeText("helloWorld"), closeSoftKeyboard());

        onView(withId(R.id.floatingEditUser)).perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        ActivityTestRule<DashboardActivity> d = new ActivityTestRule<>(DashboardActivity.class);
        d.launchActivity(new Intent());
        d.getActivity().logout();

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.loginUsernameEntryField))
                .perform(typeText("teststudent3"), closeSoftKeyboard());
        onView(withId(R.id.loginPasswordEntryField))
                .perform(typeText("helloWorld"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withId(R.id.helloMessage)).check(matches(withText("Hello, Jane!")));

        userViewActivityRule.launchActivity(new Intent());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.floatingEditUser)).perform(click());
        onView(withId(R.id.changePasswordButton)).perform(click());

        onView(withId(R.id.currentPassword)).perform(typeText("helloWorld"), closeSoftKeyboard());
        onView(withId(R.id.newPassword)).perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.confirmPassword)).perform(typeText("password"), closeSoftKeyboard());

        onView(withId(R.id.floatingEditUser)).perform(click());
    }

}
