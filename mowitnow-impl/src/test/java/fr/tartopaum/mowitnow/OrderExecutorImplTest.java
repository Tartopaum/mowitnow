package fr.tartopaum.mowitnow;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.tartopaum.mowitnow.exception.ExecutionException;
import fr.tartopaum.mowitnow.model.Coordinates;
import fr.tartopaum.mowitnow.model.Grid;
import fr.tartopaum.mowitnow.model.Order;
import fr.tartopaum.mowitnow.model.Orientation;
import fr.tartopaum.mowitnow.model.Mower;

public class OrderExecutorImplTest {

    /** L'executor à tester. */
    private OrderExecutorImpl executor;

    @Before
    public void init() {
        executor = new OrderExecutorImpl();
    }

    @Test
    public void test1() throws ExecutionException {
        Grid grid = new Grid(6, 6);
        Mower coordinates = new Mower(new Coordinates(1, 2), Orientation.NORTH);
        Iterator<Order> orders = Arrays.asList(
                Order.TURN_LEFT,
                Order.GO_FORWARD,
                Order.TURN_LEFT,
                Order.GO_FORWARD,
                Order.TURN_LEFT,
                Order.GO_FORWARD,
                Order.TURN_LEFT,
                Order.GO_FORWARD,
                Order.GO_FORWARD)
        .iterator();

        Mower result = executor.execute(grid, coordinates, orders);

        Assert.assertEquals(
                new Mower(new Coordinates(1, 3), Orientation.NORTH),
                result);
    }

    @Test
    public void test2() throws ExecutionException {
        Grid grid = new Grid(6, 6);
        Mower coordinates = new Mower(new Coordinates(3, 3), Orientation.EAST);
        Iterator<Order> orders = Arrays.asList(
                Order.GO_FORWARD,
                Order.GO_FORWARD,
                Order.TURN_RIGHT,
                Order.GO_FORWARD,
                Order.GO_FORWARD,
                Order.TURN_RIGHT,
                Order.GO_FORWARD,
                Order.TURN_RIGHT,
                Order.TURN_RIGHT,
                Order.GO_FORWARD)
        .iterator();

        Mower result = executor.execute(grid, coordinates, orders);

        Assert.assertEquals(
                new Mower(new Coordinates(5, 1), Orientation.EAST),
                result);
    }

}
