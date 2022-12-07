package edu.iastate.code42;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.PositionAssertions.isBelow;
import static android.support.test.espresso.assertion.PositionAssertions.isCompletelyBelow;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
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
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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

        ActivityTestRule<SettingsActivity> settingsActivity = new ActivityTestRule<>(SettingsActivity.class);
        settingsActivity.launchActivity(new Intent());

        settingsActivity.getActivity().settingEditor.putBoolean("is_auto_add", true);
        settingsActivity.getActivity().settingEditor.putBoolean("is_default_pass", true);
        settingsActivity.getActivity().settingEditor.putString("default_pass", "pass11");
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
    public void courseEdit(){
        courseListActivityRule.launchActivity(new Intent());
        onView(withText("Test Title")).perform(click());
        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.floatingEditCourse)).perform(click());
        onView(withId(R.id.courseTitleHeader)).perform(replaceText("Test 101"), closeSoftKeyboard());
        onView(withId(R.id.courseLanguagesView)).perform(replaceText("Python"), closeSoftKeyboard());
        onView(withId(R.id.floatingEditCourse)).perform(click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.courseTitleHeader)).check(matches(withText("Test 101")));
        onView(withId(R.id.courseDescriptionView)).check(matches(withText("Test Description")));

        courseListActivityRule.launchActivity(new Intent());
        onView(withText("Test Lang")).check(doesNotExist());
    }

    @Test
    public void courseFromList(){
        courseListActivityRule.launchActivity(new Intent());

        onView(withText("Test 101")).perform(click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.courseTitleHeader)).check(matches(withText("Test 101")));
        onView(withId(R.id.courseDescriptionView)).check(matches(withText("Test Description")));
        onView(withId(R.id.courseLanguagesView)).check(matches(withText("Python")));
    }

    @Test
    public void courseTeacherCreate() {
        courseListActivityRule.launchActivity(new Intent());

        onView(withText("Test 101")).perform(click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withId(R.id.courseTitleHeader)).perform(closeSoftKeyboard());

        onView(withId(R.id.addTeacherButton)).perform(scrollTo());
        onView(withId(R.id.addTeacherButton)).perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.addUser)).perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.editUserPassword)).check(matches(withText("pass11")));

        onView(withId(R.id.editUserFirstName))
                .perform(typeText("Donald"), closeSoftKeyboard());
        onView(withId(R.id.editUserLastName))
                .perform(typeText("Duck"), closeSoftKeyboard());
        onView(withId(R.id.editUserEmail))
                .perform(typeText("duck@iastate.edu"), closeSoftKeyboard());
        onView(withId(R.id.editUserUsername))
                .perform(typeText("duck"), closeSoftKeyboard());

        onView(withId(R.id.buttonUserCreate)).perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.buttonUserCreate)).check(doesNotExist());
        onView(withText("testteacher")).check(doesNotExist());

        onView(withText("duck")).perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.editUserFirstName))
                .check(matches(withText("Donald")));
        onView(withId(R.id.editUserLastName))
                .check(matches(withText("Duck")));
    }

    @Test
    public void courseTeacherDelete() {
        courseListActivityRule.launchActivity(new Intent());

        onView(withText("Test 101")).perform(click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.addTeacherButton)).perform(scrollTo());
        onView(withId(R.id.moreTeacherButton)).perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withText("duck")).perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.floatingEditUser)).perform(click());
        onView(withId(R.id.deleteUserButton)).perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.moreTeacherButton)).perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.addUser)).perform(click());
        onView(withText("duck")).check(doesNotExist());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withText("duck")).check(doesNotExist());
    }

    @Test
    public void courseUserAdd() {
        courseListActivityRule.launchActivity(new Intent());

        onView(withText("Test 101")).perform(click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }
        onView(withId(R.id.courseTitleHeader)).perform(closeSoftKeyboard());

        onView(withId(R.id.addStudentButton)).perform(scrollTo());
        onView(withId(R.id.addStudentButton)).perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withText("Steve Jackson")).perform(click());
        onView(withText("John Doe")).perform(click());

        onView(withId(R.id.addSelectButton)).perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withText("Steve Jackson")).check(isCompletelyBelow(withText("John Doe")));
    }

    @Test
    @Ignore
    public void courseUserEdit() {
        courseListActivityRule.launchActivity(new Intent());

        onView(withText("Test 101")).perform(click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withText("Steve Jackson")).perform(click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.floatingEditUser)).perform(click());

        String t = courseListActivityRule.getActivity().userSession.getString("token", "").toString();
        onView(withId(R.id.emailView)).perform(replaceText(t), closeSoftKeyboard());

        onView(withId(R.id.changePasswordButton)).perform(click());
        onView(withId(R.id.currentPassword)).check(doesNotExist());

        onView(withId(R.id.newPassword)).perform(typeText("password"), closeSoftKeyboard());
        onView(withId(R.id.confirmPassword)).perform(typeText("password"), closeSoftKeyboard());

        onView(withId(R.id.floatingEditUser)).perform(click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.removeMappingButton)).check(doesNotExist());
        onView(withId(R.id.emailView)).check(matches(withText(t)));
    }

    @Test
    public void courseUserUnadd() {
        courseListActivityRule.launchActivity(new Intent());

        onView(withText("Test 101")).perform(click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withText("Steve Jackson")).perform(click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.floatingEditUser)).perform(click());
        onView(withId(R.id.removeMappingButton)).perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withText("Steve Jackson")).check(doesNotExist());
    }

    @Test
    public void deleteCourse(){
        courseListActivityRule.launchActivity(new Intent());
        onView(withText("Test 101")).perform(click());
        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        onView(withId(R.id.floatingEditCourse)).perform(click());
        onView(withId(R.id.courseDelete)).perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        courseListActivityRule.launchActivity(new Intent());
        onView(withText("Test 101")).check(doesNotExist());
    }
}
