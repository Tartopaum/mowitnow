package fr.tartopaum.mowitnow.exception;

/**
 * Exception levée lors du parsing d'un fichier mowitnow.
 * @author Tartopaum
 */
public class ParseException extends Exception {

    private static final long serialVersionUID = 4567309542534118598L;

    /** Exception levée lors du parsing d'un fichier mowitnow. */
    public ParseException() {
        super();
    }

    /**
     * Exception levée lors du parsing d'un fichier mowitnow.
     * @param message Message d'erreur.
     * @param cause Exception originale.
     */
    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Exception levée lors du parsing d'un fichier mowitnow.
     * @param message Message d'erreur.
     */
    public ParseException(String message) {
        super(message);
    }

    /**
     * Exception levée lors du parsing d'un fichier mowitnow.
     * @param cause Exception originale.
     */
    public ParseException(Throwable cause) {
        super(cause);
    }

}
