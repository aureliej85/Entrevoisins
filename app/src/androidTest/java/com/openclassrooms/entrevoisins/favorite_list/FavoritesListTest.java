package com.openclassrooms.entrevoisins.favorite_list;


import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentTransaction;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.neighbour_list.NeighboursListTest;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.favorites_list.FavoritesFragment;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withTagValue;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.AllOf.allOf;

import android.support.v7.widget.RecyclerView;

@RunWith(AndroidJUnit4.class)
public class FavoritesListTest {

    private ListNeighbourActivity mActivity;
    private NeighbourApiService mApiService;


    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule = new ActivityTestRule(ListNeighbourActivity.class);


    @Before
    public void setUp() {
        mActivityRule.getActivity().getSupportFragmentManager().beginTransaction();

        mApiService = DI.getNewInstanceApiService();
    }

    @Test
    public void FavoriteTabShouldContainsOnlyFavoritesNeighbours() throws InterruptedException {
        onView(allOf(withId(R.id.list_neighbours), isDisplayed())).perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.favButton)).perform(click()).perform(pressBack());

        onView(allOf(withId(R.id.list_neighbours),isDisplayed())).perform(swipeLeft());
        Thread.sleep(300);

        onView(withId(R.id.list_favorites)).perform(RecyclerViewActions.scrollToPosition(0)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.list_favorites))).perform(actionOnItemAtPosition(3, click()));
        onView(withId(R.id.usernameText)).check(matches(withText("Caroline")));
    }


}


