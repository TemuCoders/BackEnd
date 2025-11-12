package pe.edu.upc.center.workstation.paymentsManagement.domain.exceptions;

public class InvoiceCurrencyMismatchException extends RuntimeException {
    public InvoiceCurrencyMismatchException(String message) {
        super(message);
    }
}