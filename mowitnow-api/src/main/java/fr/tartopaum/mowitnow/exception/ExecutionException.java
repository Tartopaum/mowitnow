package fr.tartopaum.mowitnow.exception;

/**
 * Exception levée lors de l'exécution d'un ordre.
 * @author Tartopaum
 */
public class ExecutionException extends Exception {

    private static final long serialVersionUID = 7829694332819648057L;

    /** Exception levée lors de l'exécution d'un ordre. */
    public ExecutionException() {
        super();
    }

    /**
     * Exception levée lors de l'exécution d'un ordre.
     * @param message Message d'erreur.
     * @param cause Exception originale.
     */
    public ExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Exception levée lors de l'exécution d'un ordre.
     * @param message Message d'erreur.
     */
    public ExecutionException(String message) {
        super(message);
    }

    /**
     * Exception levée lors de l'exécution d'un ordre.
     * @param cause Exception originale.
     */
    public ExecutionException(Throwable cause) {
        super(cause);
    }

}
