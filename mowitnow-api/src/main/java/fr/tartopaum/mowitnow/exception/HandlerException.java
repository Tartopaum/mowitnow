package fr.tartopaum.mowitnow.exception;

public class HandlerException extends Exception {

    private static final long serialVersionUID = 1398960841942816980L;

    public HandlerException() {
        super();
    }

    public HandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public HandlerException(String message) {
        super(message);
    }

    public HandlerException(Throwable cause) {
        super(cause);
    }

}
