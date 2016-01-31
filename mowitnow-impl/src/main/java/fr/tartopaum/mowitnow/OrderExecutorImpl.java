package fr.tartopaum.mowitnow;

import java.util.Iterator;

import fr.tartopaum.mowitnow.exception.ExecutionException;
import fr.tartopaum.mowitnow.model.Coordinates;
import fr.tartopaum.mowitnow.model.Grid;
import fr.tartopaum.mowitnow.model.Order;
import fr.tartopaum.mowitnow.model.Orientation;
import fr.tartopaum.mowitnow.model.Situation;

public class OrderExecutorImpl implements OrderExecutor {

    private static final Orientation[] ORIENTATION_ARRAY_ORDERED = {
            Orientation.NORTH,
            Orientation.EAST,
            Orientation.SOUTH,
            Orientation.WEST,
    };

    @Override
    public Situation execute(Grid grid, Situation situation, Order order) throws ExecutionException {
        Situation nextSituation;

        switch (order) {
        case GO_FORWARD:
            nextSituation = goForward(situation);
            break;
        case TURN_LEFT:
            nextSituation = turnLeft(situation);
            break;
        case TURN_RIGHT:
            nextSituation = turnRight(situation);
            break;
        default:
            throw new ExecutionException("Ordre non pris en charge : " + order);
        }

        return (grid.contains(nextSituation.getCoordinates()))
                ? nextSituation
                : situation;
    }

    @Override
    public Situation execute(Grid grid, Situation coordinates, Iterator<Order> orders) throws ExecutionException {
        Situation result = coordinates;

        while (orders.hasNext()) {
            Order order = orders.next();
            result = execute(grid, result, order);
        }

        return result;
    }

    private Situation goForward(Situation situation) throws ExecutionException {
        Coordinates coordinates = situation.getCoordinates();
        Coordinates nextCoordinates;

        switch (situation.getOrientation()) {
        case NORTH:
            nextCoordinates = coordinates.moveY(1);
            break;
        case EAST:
            nextCoordinates = coordinates.moveX(1);
            break;
        case SOUTH:
            nextCoordinates = coordinates.moveY(-1);
            break;
        case WEST:
            nextCoordinates = coordinates.moveX(-1);
            break;
        default:
            throw new ExecutionException("Orientation non prise en charge : " + situation.getOrientation());
        }

        return situation.withCoordinates(nextCoordinates);
    }

    private Situation turnLeft(Situation situation) throws ExecutionException {
        return turn(situation, ORIENTATION_ARRAY_ORDERED.length - 1);
    }

    private Situation turnRight(Situation situation) throws ExecutionException {
        return turn(situation, 1);
    }

    private Situation turn(Situation situation, int modifier) throws ExecutionException {
        int currentOrientationIndex = getOrientationIndex(situation.getOrientation());
        int nextOrientationIndex = (currentOrientationIndex + modifier) % ORIENTATION_ARRAY_ORDERED.length;
        return situation.withOrientation(ORIENTATION_ARRAY_ORDERED[nextOrientationIndex]);
    }

    private int getOrientationIndex(Orientation orientation) throws ExecutionException {
        for (int i = 0; i < ORIENTATION_ARRAY_ORDERED.length; i++) {
            if (ORIENTATION_ARRAY_ORDERED[i] == orientation) {
                return i;
            }
        }

        throw new ExecutionException("Modification de l'orientation non prise en charge : " + orientation);
    }

}
