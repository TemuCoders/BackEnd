package pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.query;

import pe.edu.upc.center.workstation.paymentsManagement.domain.model.queries.GetPaymentByIdQuery;
import pe.edu.upc.center.workstation.paymentsManagement.domain.model.queries.GetPaymentsByInvoiceIdQuery;
import pe.edu.upc.center.workstation.paymentsManagement.domain.services.PaymentQueryService;
import pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.resources.PaymentResource;
import pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.transform.PaymentResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", methods = {RequestMethod.GET})
@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Payments - Queries", description = "Payment read operations")
public class PaymentQueryController {

    private final PaymentQueryService paymentQueryService;

    public PaymentQueryController(PaymentQueryService paymentQueryService) {
        this.paymentQueryService = paymentQueryService;
    }

    @GetMapping("/payments/{paymentId}")
    @Operation(summary = "Get payment by ID", description = "Retrieves a single payment by its identifier")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment found"),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    public ResponseEntity<PaymentResource> getPaymentById(@PathVariable Long paymentId) {
        var query = new GetPaymentByIdQuery(paymentId);
        var optionalPayment = paymentQueryService.handle(query);

        if (optionalPayment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var resource = PaymentResourceFromEntityAssembler.toResourceFromEntity(optionalPayment.get());
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/invoices/{invoiceId}/payments")
    @Operation(summary = "Get all payments for an invoice", description = "Retrieves all payments associated with an invoice")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payments retrieved successfully")
    })
    public ResponseEntity<List<PaymentResource>> getPaymentsByInvoiceId(@PathVariable Long invoiceId) {
        var query = new GetPaymentsByInvoiceIdQuery(invoiceId);
        var payments = paymentQueryService.handle(query);

        var resources = payments.stream()
                .map(PaymentResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(resources);
    }
}