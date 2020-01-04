package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;


    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() throws InterruptedException {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        Thread.sleep(300);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void getFavoriteWithSuccess() {
        List<Neighbour> favorite = service.getFavorites();
        List<Neighbour> expectedFavorites = DummyNeighbourGenerator.FAVORITES;
        assertThat(favorite, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedFavorites.toArray()));
    }

    @Test
    public void deleteFavoriteWithSuccess() throws InterruptedException {
        Neighbour favoriteToDelete = service.getFavorites().get(0);
        Thread.sleep(300);
        service.deleteFavorite(favoriteToDelete);
        assertFalse(service.getFavorites().contains(favoriteToDelete));
    }



}
