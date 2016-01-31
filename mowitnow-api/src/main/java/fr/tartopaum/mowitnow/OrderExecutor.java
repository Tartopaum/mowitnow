package fr.tartopaum.mowitnow;

import java.util.Iterator;

import fr.tartopaum.mowitnow.exception.ExecutionException;
import fr.tartopaum.mowitnow.model.Grid;
import fr.tartopaum.mowitnow.model.Order;
import fr.tartopaum.mowitnow.model.Situation;

/**
 * Service gérant l'exécution des ordres.
 * @author Tartopaum
 */
public interface OrderExecutor {

    /**
     * Exécute un ordre pour une tondeuse, dans une grille donnée.
     *
     * @param grid Grille contenant la tondeuse.
     * @param situation Situation de la tondeuse.
     * @param order Ordre à exécuter.
     *
     * @return La situation de la tondeuse après exécution de l'ordre.
     * @throws ExecutionException
     */
    Situation execute(Grid grid, Situation situation, Order order) throws ExecutionException;

    /**
     * Exécute une série d'ordres pour une tondeuse, dans une grille donnée.
     *
     * @param grid Grille contenant la tondeuse.
     * @param situation Situation de départ de la tondeuse.
     * @param orders Série d'ordres à exécuter.
     *
     * @return La situation de la tondeuse après exécution des ordres.
     * @throws ExecutionException
     */
    Situation execute(Grid grid, Situation situation, Iterator<Order> orders) throws ExecutionException;

}
