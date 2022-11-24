package edu.iastate.code42;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;


import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewAction;
import android.support.test.filters.LargeTest;
import android.support.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExpressoSystemTest {
    private static final int SIMULATED_DELAY_MS = 500;

    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setup(){

    }

    @Test
    public void loginSuccess(){
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

        assertEquals(mainActivityRule.getActivity().userSession.contains("token"),true);
    }

    @After
    public void destroy(){
        mainActivityRule.getActivity().userSessionEditor.clear();
        mainActivityRule.getActivity().userSessionEditor.commit();
    }

}
