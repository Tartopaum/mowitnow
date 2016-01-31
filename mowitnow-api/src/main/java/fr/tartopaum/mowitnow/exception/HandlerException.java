package fr.tartopaum.mowitnow.exception;

/**
 * Exception levée lors du traitement d'un handler.
 * @author Tartopaum
 */
public class HandlerException extends Exception {

    private static final long serialVersionUID = 1398960841942816980L;

    /** Exception levée lors du traitement d'un handler. */
    public HandlerException() {
        super();
    }

    /**
     * Exception levée lors du traitement d'un handler.
     * @param message Message d'erreur.
     * @param cause Exception originale.
     */
    public HandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Exception levée lors du traitement d'un handler.
     * @param message Message d'erreur.
     */
    public HandlerException(String message) {
        super(message);
    }

    /**
     * Exception levée lors du traitement d'un handler.
     * @param cause Exception originale.
     */
    public HandlerException(Throwable cause) {
        super(cause);
    }

}
