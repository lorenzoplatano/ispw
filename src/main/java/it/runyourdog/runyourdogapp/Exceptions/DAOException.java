package it.runyourdog.runyourdogapp.Exceptions;

public class DAOException extends Exception {

    public DAOException(String message) { super( "DAO error: " + message); }
    public DAOException(String message, Throwable cause) { super(message, cause); }
}
