package com.openclassrooms.entrevoisins.favorite_list;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteFavoriteViewAction;
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
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withChild;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withTagValue;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.AllOf.allOf;


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
        onView(withId(R.id.list_favorites)).perform(scrollToPosition(0)).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.list_favorites))).perform(actionOnItemAtPosition(3, click()));
        onView(withId(R.id.usernameText)).check(matches(withText("Caroline")));

        //onView(withId(R.id.detailNeighbour)).check(matches(isDisplayed()));
        //onView(withId(R.id.favButton)).check(matches(withTagValue(equalTo(R.drawable.ic_star_yellow))));
    }

    @Test
    public void favoriteList_deleteAction_shouldRemoveItem() throws InterruptedException {
        onView(allOf(withId(R.id.list_neighbours),isDisplayed())).perform(swipeLeft());
        Thread.sleep(300);

        onView(ViewMatchers.withId(R.id.list_favorites)).check(withItemCount(3));
        onView(ViewMatchers.withId(R.id.list_favorites))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteFavoriteViewAction()));

        Thread.sleep(300);
        onView(withText("OUI")).perform(click());
        onView(ViewMatchers.withId(R.id.list_favorites)).check(withItemCount(2));
    }

}


