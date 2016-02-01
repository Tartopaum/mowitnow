package fr.tartopaum.mowitnow;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.tartopaum.mowitnow.exception.ExecutionException;
import fr.tartopaum.mowitnow.model.Coordinates;
import fr.tartopaum.mowitnow.model.Grid;
import fr.tartopaum.mowitnow.model.Mower;
import fr.tartopaum.mowitnow.model.Order;
import fr.tartopaum.mowitnow.model.Orientation;

public class OrderExecutorImpl implements OrderExecutor {

    private static final Logger LOG = LoggerFactory.getLogger(OrderExecutorImpl.class);

    /** Liste des orientations ordonnées dans le sens des aiguilles d'une montre. */
    private static final Orientation[] ORIENTATION_ARRAY_ORDERED = {
            Orientation.NORTH,
            Orientation.EAST,
            Orientation.SOUTH,
            Orientation.WEST,
    };

    @Override
    public Mower execute(Grid grid, Mower mower, Order order) throws ExecutionException {
        Mower nextSituation;

        switch (order) {
        case GO_FORWARD:
            nextSituation = goForward(mower);
            break;
        case TURN_LEFT:
            nextSituation = turnLeft(mower);
            break;
        case TURN_RIGHT:
            nextSituation = turnRight(mower);
            break;
        default:
            throw new ExecutionException("Ordre non pris en charge : " + order);
        }

        Mower result = (grid.contains(nextSituation.getCoordinates()))
                ? nextSituation
                : mower;

        if (LOG.isDebugEnabled()) {
            LOG.debug("execute({}, {}, {}) => {}",
                    grid, mower, order,
                    result);
        }

        return result;
    }

    @Override
    public Mower execute(Grid grid, Mower mower, Iterator<Order> orders) throws ExecutionException {
        Mower result = mower;

        while (orders.hasNext()) {
            Order order = orders.next();
            result = execute(grid, result, order);
        }

        return result;
    }

    /**
     * Exécution de l'ordre d'avancer.
     * @param mower Situation initiale.
     * @return Situation finale.
     * @throws ExecutionException
     */
    private Mower goForward(Mower mower) throws ExecutionException {
        Coordinates coordinates = mower.getCoordinates();
        Coordinates nextCoordinates;

        switch (mower.getOrientation()) {
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
            throw new ExecutionException("Orientation non prise en charge : " + mower.getOrientation());
        }

        return mower.withCoordinates(nextCoordinates);
    }

    /**
     * Exécution de l'ordre de tourner à gauche.
     * @param mower Situation initiale.
     * @return Situation finale.
     * @throws ExecutionException
     */
    private Mower turnLeft(Mower mower) throws ExecutionException {
        return turn(mower, ORIENTATION_ARRAY_ORDERED.length - 1);
    }

    /**
     * Exécution de l'ordre de tourner à droite.
     * @param mower Situation initiale.
     * @return Situation finale.
     * @throws ExecutionException
     */
    private Mower turnRight(Mower mower) throws ExecutionException {
        return turn(mower, 1);
    }

    /**
     * Exécution d'un ordre de rotation dans le sens des aiguilles d'une montre.
     * @param mower Situation initiale.
     * @param modifier De combien de framents tourner.
     * @return Situation finale.
     * @throws ExecutionException
     */
    private Mower turn(Mower mower, int modifier) throws ExecutionException {
        int currentOrientationIndex = getOrientationIndex(mower.getOrientation());
        int nextOrientationIndex = (currentOrientationIndex + modifier) % ORIENTATION_ARRAY_ORDERED.length;
        return mower.withOrientation(ORIENTATION_ARRAY_ORDERED[nextOrientationIndex]);
    }

    /**
     * Retourne l'index de l'orientation passée en paramètre dans le tableau contenant les orientations.
     * @param orientation Orientation dont on veut récupérer l'index.
     * @return index de l'orientation passée en paramètre dans le tableau contenant les orientations.
     * @throws ExecutionException
     */
    private int getOrientationIndex(Orientation orientation) throws ExecutionException {
        for (int i = 0; i < ORIENTATION_ARRAY_ORDERED.length; i++) {
            if (ORIENTATION_ARRAY_ORDERED[i] == orientation) {
                return i;
            }
        }

        throw new ExecutionException("Modification de l'orientation non prise en charge : " + orientation);
    }

}
