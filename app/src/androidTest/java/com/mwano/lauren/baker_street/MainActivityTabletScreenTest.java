package com.mwano.lauren.baker_street;


import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.mwano.lauren.baker_street.ui.main.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * This test demos a user clicking on a GridView item in MainActivity on a tablet which displays the
 * corresponding MasterIngredientsPageFragment with the recipe name on the right side of the screen
 *
 * This test does not utilize Idling Resources yet. If idling is set in the MainActivity,
 * then this test will fail.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTabletScreenTest {

        public static final String RECIPE_NAME = "Brownies";

        @Rule
        public ActivityTestRule<MainActivity> mActivityTestRule =
                new ActivityTestRule<>(MainActivity.class);

        @Test
        public void clickGridViewItemOnTablet_DisplaysIngredientsFragment() {

            // Get a reference to the gridview item and click it
            onView(ViewMatchers.withId(R.id.card_recycler_view))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));


            // Check that the MasterIngredientsPageFragment opens with the correct recipe name
            // displayed at the top
            onView(withId(R.id.tv_recipe_name_main)).check(matches(withText(RECIPE_NAME)));

        }
    }