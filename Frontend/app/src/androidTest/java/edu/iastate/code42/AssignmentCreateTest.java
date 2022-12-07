package edu.iastate.code42;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
//import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import android.content.Intent;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AssignmentCreateTest {
    private static final int SIMULATED_DELAY_MS = 1000;

    public void simDelay(){

    }
    @Rule
    public ActivityTestRule<AssignmentCreateActivity> AssignmentCreateActivityRule = new
            ActivityTestRule<>(AssignmentCreateActivity.class);

    @Rule
    public ActivityTestRule<AssignmentCodeCreateActivity> AssignmentCodeCreateActivityRule = new
            ActivityTestRule<>(AssignmentCodeCreateActivity.class);

    @BeforeClass
    public static void setup(){
        ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(MainActivity.class);
        ActivityTestRule<CoursesActivity> courseListActivityRule = new
                ActivityTestRule<>(CoursesActivity.class);
        mainActivityRule.launchActivity(new Intent());

        onView(withId(R.id.loginUsernameEntryField))
                .perform(typeText("testadmin"), closeSoftKeyboard());
        onView(withId(R.id.loginPasswordEntryField))
                .perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {}
        courseListActivityRule.launchActivity(new Intent());
        onView(withText("Test 101")).perform(click());

        try {Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {}

        onView(withId(R.id.addAssignmentButton)).perform(click());
        try {Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {}
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
    public void assignmentCreateIncorrect(){
        AssignmentCreateActivityRule.launchActivity(new Intent());
        onView(withId(R.id.assignmentNameEnter))
                .perform(typeText("Test Assignment Name"), closeSoftKeyboard());
        onView(withId(R.id.description))
                .perform(typeText("Test Description"), closeSoftKeyboard());
        onView(withId(R.id.problemStatement))
                .perform(typeText("Test Problem Statement"), closeSoftKeyboard());
        onView(withId(R.id.spinner)).perform(click());
        onView(withText("Java")).perform(click());

        onView(withId(R.id.nextButton)).perform(click());

        // Put thread to sleep to wait for the screen transition to take place
        try {Thread.sleep(SIMULATED_DELAY_MS);} catch (InterruptedException e) {}

        // We shouldnt get to the next screen, as the points havent been set
        onView(withText("Enter code that student will start with:")).check(doesNotExist());
        try {Thread.sleep(SIMULATED_DELAY_MS);} catch (InterruptedException e) {}
    }
    @Test
    public void assignmentCreateCorrect(){
        AssignmentCreateActivityRule.launchActivity(new Intent());

        onView(withId(R.id.assignmentNameEnter))
                .perform(typeText("Test Assignment Name"), closeSoftKeyboard());
        onView(withId(R.id.description))
                .perform(typeText("Test Description"), closeSoftKeyboard());
        onView(withId(R.id.problemStatement))
                .perform(typeText("Test Problem Statement"), closeSoftKeyboard());
        onView(withId(R.id.spinner)).perform(click());
        onView(withText("Java")).perform(click());
        onView(withId(R.id.scoreNumber))
                .perform(typeText("420"), closeSoftKeyboard());

        onView(withId(R.id.nextButton)).perform(click());
        // now we should
        try {Thread.sleep(SIMULATED_DELAY_MS);} catch (InterruptedException e) {}
        onView(withText("Enter code that student will start with:")).check(matches(isDisplayed()));

    }
    @Test
    public void assignmentCreateIncorrect2(){
        AssignmentCreateActivityRule.launchActivity(new Intent());

        onView(withId(R.id.assignmentNameEnter))
                .perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.description))
                .perform(typeText("Test Description"), closeSoftKeyboard());
        onView(withId(R.id.problemStatement))
                .perform(typeText("Test Problem Statement"), closeSoftKeyboard());
        onView(withId(R.id.spinner)).perform(click());
        onView(withText("Python")).perform(click());
        onView(withId(R.id.scoreNumber))
                .perform(typeText(""), closeSoftKeyboard());

        onView(withId(R.id.nextButton)).perform(click());
        // now we should
        try {Thread.sleep(SIMULATED_DELAY_MS);} catch (InterruptedException e) {}
        onView(withText("Enter code that student will start with:")).check(doesNotExist());

    }
    public void assignmentCreateIncorrect3(){
        AssignmentCreateActivityRule.launchActivity(new Intent());

        onView(withId(R.id.assignmentNameEnter))
                .perform(typeText("Test Assignment Name"), closeSoftKeyboard());
        onView(withId(R.id.description))
                .perform(typeText("T"), closeSoftKeyboard());
        onView(withId(R.id.problemStatement))
                .perform(typeText("Test Problem Statement"), closeSoftKeyboard());
        onView(withId(R.id.spinner)).perform(click());
        onView(withText("C")).perform(click());
        onView(withId(R.id.scoreNumber))
                .perform(typeText(""), closeSoftKeyboard());

        onView(withId(R.id.nextButton)).perform(click());
        // now we should
        try {Thread.sleep(SIMULATED_DELAY_MS);} catch (InterruptedException e) {}
        onView(withText("Enter code that student will start with:")).check(doesNotExist());

    }
}
