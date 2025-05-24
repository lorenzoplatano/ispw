package it.runyourdog.runyourdogapp.exceptions;

public class PersistenceConfigurationException extends Exception {

    public PersistenceConfigurationException() {
        super("Errore durante la scelta della persistenza.");
    }

    public PersistenceConfigurationException(String message) {
        super(message);
    }

    public PersistenceConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersistenceConfigurationException(Throwable cause) {
        super(cause);
    }
}