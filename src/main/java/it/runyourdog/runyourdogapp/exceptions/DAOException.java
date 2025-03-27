package it.runyourdog.runyourdogapp.exceptions;

public class DAOException extends Exception {

    public DAOException(String message) { super( "DAO error: " + message); }
    public DAOException(String message, Throwable cause) { super(message, cause); }
}
