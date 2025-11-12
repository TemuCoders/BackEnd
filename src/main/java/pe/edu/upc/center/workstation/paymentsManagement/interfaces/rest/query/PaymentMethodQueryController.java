package pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.query;

import pe.edu.upc.center.workstation.paymentsManagement.domain.model.queries.GetPaymentMethodsByUserIdQuery;
import pe.edu.upc.center.workstation.paymentsManagement.domain.services.PaymentMethodQueryService;
import pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.resources.PaymentMethodResource;
import pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.transform.PaymentMethodResourceFromEntityAssembler;
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
@RequestMapping(value = "/api/v1/payment-methods", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Payment Methods - Queries", description = "Payment method read operations")
public class PaymentMethodQueryController {

    private final PaymentMethodQueryService paymentMethodQueryService;

    public PaymentMethodQueryController(PaymentMethodQueryService paymentMethodQueryService) {
        this.paymentMethodQueryService = paymentMethodQueryService;
    }

    @GetMapping
    @Operation(summary = "Get payment methods by user", description = "Retrieves all payment methods for a specific user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Payment methods retrieved successfully")
    })
    public ResponseEntity<List<PaymentMethodResource>> getPaymentMethodsByUser(
            @RequestParam Long userId
    ) {
        var query = new GetPaymentMethodsByUserIdQuery(userId);
        var paymentMethods = paymentMethodQueryService.handle(query);

        var resources = paymentMethods.stream()
                .map(PaymentMethodResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(resources);
    }
}
