package fr.tartopaum.mowitnow.exception;

public class ParseException extends Exception {

    private static final long serialVersionUID = 4567309542534118598L;

    public ParseException() {
        super();
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseException(String message) {
        super(message);
    }

    public ParseException(Throwable cause) {
        super(cause);
    }

}
