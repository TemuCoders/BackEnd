package pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.command;

import pe.edu.upc.center.workstation.paymentsManagement.domain.model.commands.DisablePaymentMethodCommand;
import pe.edu.upc.center.workstation.paymentsManagement.domain.model.queries.GetPaymentMethodByIdQuery;
import pe.edu.upc.center.workstation.paymentsManagement.domain.services.PaymentMethodCommandService;
import pe.edu.upc.center.workstation.paymentsManagement.domain.services.PaymentMethodQueryService;
import pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.resources.AddPaymentMethodResource;
import pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.resources.PaymentMethodResource;
import pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.transform.AddPaymentMethodCommandFromResourceAssembler;
import pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.transform.PaymentMethodResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.DELETE})
@RestController
@RequestMapping(value = "/api/v1/payment-methods", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Payment Methods - Commands", description = "Payment method write operations")
public class PaymentMethodCommandController {

    private final PaymentMethodCommandService paymentMethodCommandService;
    private final PaymentMethodQueryService paymentMethodQueryService;

    public PaymentMethodCommandController(
            PaymentMethodCommandService paymentMethodCommandService,
            PaymentMethodQueryService paymentMethodQueryService
    ) {
        this.paymentMethodCommandService = paymentMethodCommandService;
        this.paymentMethodQueryService = paymentMethodQueryService;
    }

    @PostMapping
    @Operation(summary = "Add a payment method", description = "Registers a new payment method for a user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Payment method added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<PaymentMethodResource> addPaymentMethod(
            @Valid @RequestBody AddPaymentMethodResource resource
    ) {
        var command = AddPaymentMethodCommandFromResourceAssembler.toCommandFromResource(resource);
        var paymentMethodId = paymentMethodCommandService.handle(command);

        if (paymentMethodId == null || paymentMethodId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        var query = new GetPaymentMethodByIdQuery(paymentMethodId);
        var optionalPaymentMethod = paymentMethodQueryService.handle(query);

        if (optionalPaymentMethod.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var paymentMethodResource = PaymentMethodResourceFromEntityAssembler
                .toResourceFromEntity(optionalPaymentMethod.get());

        return new ResponseEntity<>(paymentMethodResource, HttpStatus.CREATED);
    }

    @DeleteMapping("/{paymentMethodId}")
    @Operation(summary = "Disable a payment method", description = "Marks a payment method as disabled")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Payment method disabled successfully"),
            @ApiResponse(responseCode = "404", description = "Payment method not found")
    })
    public ResponseEntity<?> disablePaymentMethod(@PathVariable Long paymentMethodId) {
        var command = new DisablePaymentMethodCommand(paymentMethodId);
        paymentMethodCommandService.handle(command);
        return ResponseEntity.noContent().build();
    }
}