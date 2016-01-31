package fr.tartopaum.mowitnow.exception;

public class ExecutionException extends Exception {

    private static final long serialVersionUID = 7829694332819648057L;

    public ExecutionException() {
        super();
    }

    public ExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExecutionException(String message) {
        super(message);
    }

    public ExecutionException(Throwable cause) {
        super(cause);
    }

}
