package it.runyourdog.runyourdogapp.exceptions;

public class ConnectionException extends Exception{

    public ConnectionException(String message, Throwable cause) { super(message, cause); }
    public ConnectionException(String message) { super(message); }
}
