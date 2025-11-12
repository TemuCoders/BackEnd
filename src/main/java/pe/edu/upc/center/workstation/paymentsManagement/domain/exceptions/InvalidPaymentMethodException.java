package pe.edu.upc.center.workstation.paymentsManagement.domain.exceptions;

public class InvalidPaymentMethodException extends RuntimeException {
    public InvalidPaymentMethodException(String message) {
        super(message);
    }
}