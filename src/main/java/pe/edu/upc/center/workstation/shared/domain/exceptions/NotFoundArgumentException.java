package pe.edu.upc.center.workstation.shared.domain.exceptions;

public class NotFoundArgumentException extends RuntimeException {

  public NotFoundArgumentException(String message) {
    super(message);
  }

}