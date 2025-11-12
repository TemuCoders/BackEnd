package pe.edu.upc.center.workstation.paymentsManagement.domain.exceptions;

import pe.edu.upc.center.workstation.paymentsManagement.domain.model.valueobjects.PaymentStatus;

public class InvalidPaymentStatusTransitionException extends RuntimeException {
    public InvalidPaymentStatusTransitionException(PaymentStatus from, PaymentStatus to) {
        super("Cannot transition payment status from " + from + " to " + to);
    }
}