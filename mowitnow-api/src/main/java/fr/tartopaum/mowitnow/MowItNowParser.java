package fr.tartopaum.mowitnow;

import java.io.Reader;

import fr.tartopaum.mowitnow.exception.HandlerException;
import fr.tartopaum.mowitnow.exception.ParseException;

/**
 * Parseur de fichiers de configuration mowitnow.
 * @author Tartopaum
 */
public interface MowItNowParser {

    /**
     * Parse le reader passé en entrée.
     * Le travail est délégué au handler.
     * @param reader Reader sur le fichier à lire.
     * @param handler Handler gérant les actions à effectuer.
     * @throws ParseException Exception levée en cas d'erreur de parsing.
     * @throws HandlerException Exception levée par le handler, en cas d'erreur de traitement.
     */
    void parse(Reader reader, MowItNowHandler handler) throws ParseException, HandlerException;

}
