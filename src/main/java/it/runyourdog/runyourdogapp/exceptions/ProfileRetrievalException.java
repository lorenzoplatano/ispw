package it.runyourdog.runyourdogapp.Exceptions;

public class ProfileRetrievalException extends Exception {
  public ProfileRetrievalException(String message) {
    super(message);
  }

  public ProfileRetrievalException(String message, Throwable cause) {
    super(message, cause);
  }
}
