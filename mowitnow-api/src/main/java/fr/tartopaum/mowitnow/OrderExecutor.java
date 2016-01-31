package fr.tartopaum.mowitnow;

import java.util.Iterator;

import fr.tartopaum.mowitnow.exception.ExecutionException;
import fr.tartopaum.mowitnow.model.Grid;
import fr.tartopaum.mowitnow.model.Order;
import fr.tartopaum.mowitnow.model.Situation;

public interface OrderExecutor {

    Situation execute(Grid grid, Situation coordinates, Order order) throws ExecutionException;
    Situation execute(Grid grid, Situation coordinates, Iterator<Order> orders) throws ExecutionException;

}
