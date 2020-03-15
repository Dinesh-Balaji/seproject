package com.example.seproject;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity>mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    private String word="BITCH";

    @Before
    public void setUp() throws Exception {

    }
    @Test
    public void testUserInputScenario()
    {
        //input some text in the edit text
        Espresso.onView(withId(R.id.comment_id)).perform(typeText(word));
        //close soft keyboard
        Espresso.closeSoftKeyboard();

        Espresso.onView(withId(R.id.submit_id)).perform(click());
        //checking the text in the textview
        //  Espresso.onView(withId(R.id.text_id)).check(matches(withText(word)));


    }

    @After
    public void tearDown() throws Exception {
    }



}