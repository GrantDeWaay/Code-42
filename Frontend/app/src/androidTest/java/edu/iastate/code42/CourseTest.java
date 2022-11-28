package edu.iastate.code42;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import android.content.Intent;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CourseTest {
    private static final int SIMULATED_DELAY_MS = 1000;

    @Rule
    public ActivityTestRule<CourseCreationActivity> courseCreationActivityRule = new
            ActivityTestRule<>(CourseCreationActivity.class);

    @Rule
    public ActivityTestRule<CoursesActivity> courseListActivityRule = new
            ActivityTestRule<>(CoursesActivity.class);

    @BeforeClass
    public static void setup(){
        ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(MainActivity.class);
        mainActivityRule.launchActivity(new Intent());

        onView(withId(R.id.loginUsernameEntryField))
                .perform(typeText("testadmin"), closeSoftKeyboard());
        onView(withId(R.id.loginPasswordEntryField))
                .perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
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
    public void courseCreation(){
        courseCreationActivityRule.launchActivity(new Intent());

        onView(withId(R.id.editCourseTitle))
                .perform(typeText("Test Title"), closeSoftKeyboard());
        onView(withId(R.id.courseDescriptionView))
                .perform(typeText("Test Description"), closeSoftKeyboard());
        onView(withId(R.id.courseLanguagesView))
                .perform(typeText("Test Lang"), closeSoftKeyboard());

        onView(withId(R.id.createCourse)).perform(click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.courseTitleHeader)).check(matches(withText("Test Title")));
        onView(withId(R.id.courseDescriptionView)).check(matches(withText("Test Description")));
    }

    @Test
    public void courseViewFromList(){
        courseListActivityRule.launchActivity(new Intent());

        onView(withText("Test Title")).perform(click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.courseTitleHeader)).check(matches(withText("Test Title")));
        onView(withId(R.id.courseDescriptionView)).check(matches(withText("Test Description")));
    }

    @Test
    public void editCourse(){

    }

    @Test
    public void deleteCourse(){

    }

}
