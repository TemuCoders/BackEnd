package pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.command;

import pe.edu.upc.center.workstation.paymentsManagement.domain.model.commands.RefundPaymentCommand;
import pe.edu.upc.center.workstation.paymentsManagement.domain.model.queries.GetPaymentByIdQuery;
import pe.edu.upc.center.workstation.paymentsManagement.domain.services.PaymentCommandService;
import pe.edu.upc.center.workstation.paymentsManagement.domain.services.PaymentQueryService;
import pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.resources.CreatePaymentResource;
import pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.resources.PaymentResource;
import pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.transform.CreatePaymentCommandFromResourceAssembler;
import pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.transform.PaymentResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", methods = {RequestMethod.POST})
@RestController
@RequestMapping(value = "/api/v1/payments", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Payments - Commands", description = "Payment write operations")
public class PaymentCommandController {

    private final PaymentCommandService paymentCommandService;
    private final PaymentQueryService paymentQueryService;

    public PaymentCommandController(
            PaymentCommandService paymentCommandService,
            PaymentQueryService paymentQueryService
    ) {
        this.paymentCommandService = paymentCommandService;
        this.paymentQueryService = paymentQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a new payment", description = "Processes a payment for an invoice")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Payment created and processed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or payment already exists"),
            @ApiResponse(responseCode = "404", description = "Invoice, User, Booking or Payment Method not found")
    })
    public ResponseEntity<PaymentResource> createPayment(@Valid @RequestBody CreatePaymentResource resource) {
        var command = CreatePaymentCommandFromResourceAssembler.toCommandFromResource(resource);
        var paymentId = paymentCommandService.handle(command);

        if (paymentId == null || paymentId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        var query = new GetPaymentByIdQuery(paymentId);
        var optionalPayment = paymentQueryService.handle(query);

        if (optionalPayment.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var paymentResource = PaymentResourceFromEntityAssembler.toResourceFromEntity(optionalPayment.get());
        return new ResponseEntity<>(paymentResource, HttpStatus.CREATED);
    }

    @PostMapping("/{paymentId}/refund")
    @Operation(summary = "Refund a payment", description = "Processes a refund for a payment")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment refunded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid state transition"),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    public ResponseEntity<PaymentResource> refundPayment(@PathVariable Long paymentId) {
        var command = new RefundPaymentCommand(paymentId);
        var optionalPayment = paymentCommandService.handle(command);

        if (optionalPayment.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var paymentResource = PaymentResourceFromEntityAssembler.toResourceFromEntity(optionalPayment.get());
        return ResponseEntity.ok(paymentResource);
    }
}