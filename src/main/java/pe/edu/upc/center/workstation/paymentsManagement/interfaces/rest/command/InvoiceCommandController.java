package pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.command;

import pe.edu.upc.center.workstation.paymentsManagement.domain.model.commands.SendInvoiceCommand;
import pe.edu.upc.center.workstation.paymentsManagement.domain.model.queries.GetInvoiceByIdQuery;
import pe.edu.upc.center.workstation.paymentsManagement.domain.services.InvoiceCommandService;
import pe.edu.upc.center.workstation.paymentsManagement.domain.services.InvoiceQueryService;
import pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.resources.CreateInvoiceResource;
import pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.resources.InvoiceResource;
import pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.transform.CreateInvoiceCommandFromResourceAssembler;
import pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.transform.InvoiceResourceFromEntityAssembler;
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
@RequestMapping(value = "/api/v1/invoices", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Invoices - Commands", description = "Invoice write operations")
public class InvoiceCommandController {

    private final InvoiceCommandService invoiceCommandService;
    private final InvoiceQueryService invoiceQueryService;

    public InvoiceCommandController(
            InvoiceCommandService invoiceCommandService,
            InvoiceQueryService invoiceQueryService
    ) {
        this.invoiceCommandService = invoiceCommandService;
        this.invoiceQueryService = invoiceQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a new invoice", description = "Creates an invoice for a booking")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Invoice created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or duplicate invoice"),
            @ApiResponse(responseCode = "404", description = "User or Booking not found")
    })
    public ResponseEntity<InvoiceResource> createInvoice(@Valid @RequestBody CreateInvoiceResource resource) {
        var command = CreateInvoiceCommandFromResourceAssembler.toCommandFromResource(resource);
        var invoiceId = invoiceCommandService.handle(command);

        if (invoiceId == null || invoiceId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        var query = new GetInvoiceByIdQuery(invoiceId);
        var optionalInvoice = invoiceQueryService.handle(query);

        if (optionalInvoice.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var invoiceResource = InvoiceResourceFromEntityAssembler.toResourceFromEntity(optionalInvoice.get());
        return new ResponseEntity<>(invoiceResource, HttpStatus.CREATED);
    }

    @PostMapping("/{invoiceId}/send")
    @Operation(summary = "Send an invoice", description = "Marks an invoice as sent")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Invoice sent successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid state transition"),
            @ApiResponse(responseCode = "404", description = "Invoice not found")
    })
    public ResponseEntity<InvoiceResource> sendInvoice(@PathVariable Long invoiceId) {
        var command = new SendInvoiceCommand(invoiceId);
        var optionalInvoice = invoiceCommandService.handle(command);

        if (optionalInvoice.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var invoiceResource = InvoiceResourceFromEntityAssembler.toResourceFromEntity(optionalInvoice.get());
        return ResponseEntity.ok(invoiceResource);
    }
}