package pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.query;

import pe.edu.upc.center.workstation.paymentsManagement.domain.model.queries.GetInvoiceByBookingIdQuery;
import pe.edu.upc.center.workstation.paymentsManagement.domain.model.queries.GetInvoiceByIdQuery;
import pe.edu.upc.center.workstation.paymentsManagement.domain.services.InvoiceQueryService;
import pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.resources.InvoiceResource;
import pe.edu.upc.center.workstation.paymentsManagement.interfaces.rest.transform.InvoiceResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", methods = {RequestMethod.GET})
@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Invoices - Queries", description = "Invoice read operations")
public class InvoiceQueryController {

    private final InvoiceQueryService invoiceQueryService;

    public InvoiceQueryController(InvoiceQueryService invoiceQueryService) {
        this.invoiceQueryService = invoiceQueryService;
    }

    @GetMapping("/invoices/{invoiceId}")
    @Operation(summary = "Get invoice by ID", description = "Retrieves a single invoice by its identifier")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Invoice found"),
            @ApiResponse(responseCode = "404", description = "Invoice not found")
    })
    public ResponseEntity<InvoiceResource> getInvoiceById(@PathVariable Long invoiceId) {
        var query = new GetInvoiceByIdQuery(invoiceId);
        var optionalInvoice = invoiceQueryService.handle(query);

        if (optionalInvoice.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var resource = InvoiceResourceFromEntityAssembler.toResourceFromEntity(optionalInvoice.get());
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/bookings/{bookingId}/invoice")
    @Operation(summary = "Get invoice by booking ID", description = "Retrieves invoice associated with a booking")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Invoice found"),
            @ApiResponse(responseCode = "404", description = "Invoice not found")
    })
    public ResponseEntity<InvoiceResource> getInvoiceByBookingId(@PathVariable Long bookingId) {
        var query = new GetInvoiceByBookingIdQuery(bookingId);
        var optionalInvoice = invoiceQueryService.handle(query);

        if (optionalInvoice.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var resource = InvoiceResourceFromEntityAssembler.toResourceFromEntity(optionalInvoice.get());
        return ResponseEntity.ok(resource);
    }
}