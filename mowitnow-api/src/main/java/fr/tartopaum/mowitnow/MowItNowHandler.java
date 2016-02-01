package fr.tartopaum.mowitnow;

import fr.tartopaum.mowitnow.exception.HandlerException;
import fr.tartopaum.mowitnow.model.Grid;
import fr.tartopaum.mowitnow.model.Order;
import fr.tartopaum.mowitnow.model.Mower;

/**
 * Handler gérant les actions à effectuer lors du parsing d'un fichier mowitnow.
 * @author Tartopaum
 */
public interface MowItNowHandler {

    /**
     * Méthode appelée lors du parsing de la première ligne du fichier, contenant la définition de la grille.
     * @param grid Définition de la grille.
     * @throws HandlerException
     */
    void begin(Grid grid) throws HandlerException;

    /**
     * Méthode appelée lorsque la fin du fichier est atteinte.
     * @throws HandlerException
     */
    void end() throws HandlerException;

    /**
     * Méthode appelée lors du parsing de la première ligne d'une tondeuse, contenant sa situation initiale.
     * @param mower Situation initiale de la tondeuse.
     * @throws HandlerException
     */
    void beginMower(Mower mower) throws HandlerException;

    /**
     * Méthode appelée lorsque toutes les actions de la tondeuse ont été appelées.
     * @throws HandlerException
     */
    void endMower() throws HandlerException;

    /**
     * Méthode appelée lorsqu'un ordre est rencontré pour la tondeuse.
     * @param order Ordre pour la tondeuse actuelle.
     * @throws HandlerException
     */
    void order(Order order) throws HandlerException;

}
