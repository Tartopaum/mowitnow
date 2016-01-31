package fr.tartopaum.mowitnow;

import fr.tartopaum.mowitnow.exception.HandlerException;
import fr.tartopaum.mowitnow.model.Grid;
import fr.tartopaum.mowitnow.model.Order;
import fr.tartopaum.mowitnow.model.Situation;

public interface MowItNowHandler {

    void begin(Grid grid) throws HandlerException;
    void end() throws HandlerException;

    void beginMower(Situation situation) throws HandlerException;
    void endMower() throws HandlerException;

    void order(Order order) throws HandlerException;

}
