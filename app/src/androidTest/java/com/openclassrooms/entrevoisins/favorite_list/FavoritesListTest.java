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
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.AllOf.allOf;


@RunWith(AndroidJUnit4.class)
public class FavoritesListTest {


    private NeighbourApiService mApiService;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule = new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mApiService = DI.getNewInstanceApiService();
    }

    @Test
    public void FavoriteTabShouldContainsOnlyFavoritesNeighbours(){
        onView(allOf(withId(R.id.list_neighbours),isDisplayed())).perform(swipeLeft());
        onView(withId(R.id.list_favorites)).perform(scrollToPosition(0)).check(matches(isDisplayed()));
        onView(withText("Emma")).check(matches(withText(mApiService.getFavorites().get(0).getName())));
    }



    @Test
    public void favoriteList_deleteAction_shouldRemoveItem(){
        onView(allOf(withId(R.id.list_neighbours),isDisplayed())).perform(swipeLeft());

        onView(ViewMatchers.withId(R.id.list_favorites)).check(withItemCount(3));
        onView(ViewMatchers.withId(R.id.list_favorites))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteFavoriteViewAction()));

        onView(withText("OUI")).perform(click());
        onView(ViewMatchers.withId(R.id.list_favorites)).check(withItemCount(2));
    }

}


