package pe.edu.upc.center.workstation.shared.domain.exceptions;

public class NotFoundIdException extends RuntimeException {

  public NotFoundIdException(Class<?> entityClass, Long id) {
    super(String.format("%s with id %s does not exist.", entityClass.getSimpleName(), id));
  }
}
