package pe.com.facturease.platform.u202422298.billing.interfaces.rest;

import pe.com.facturease.platform.u202422298.billing.application.commandservices.InvoiceCommandService;
import pe.com.facturease.platform.u202422298.billing.interfaces.rest.resources.CreateInvoiceResource;
import pe.com.facturease.platform.u202422298.billing.interfaces.rest.transform.CreateInvoiceCommandFromResourceAssembler;
import pe.com.facturease.platform.u202422298.billing.interfaces.rest.transform.InvoiceResourceFromEntityAssembler;
import pe.com.facturease.platform.u202422298.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Locale;

/** REST entry point for corporate invoice creation.
 * @author Replace With Your Full Name
 */
@RestController
@RequestMapping(value = "/api/v1/invoices", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Invoices", description = "Corporate electronic invoices")
public class InvoicesController {
    private final InvoiceCommandService service;
    private final MessageSource messages;
    public InvoicesController(InvoiceCommandService service, MessageSource messages) {
        this.service = service;
        this.messages = messages;
    }

    /** Registers an invoice and returns the generated ID.
     * @param resource request data
     * @param locale resolved request locale
     * @return HTTP 201 resource or localized error
     */
    @Operation(summary = "Create a corporate electronic invoice",
            description = "Validates the 18 percent tax and item count rules before storing the invoice.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Invoice created"),
            @ApiResponse(responseCode = "400", description = "Invalid input or tax value"),
            @ApiResponse(responseCode = "409", description = "Duplicate invoice identifier"),
            @ApiResponse(responseCode = "422", description = "ISSUED or PAID invoice has zero items")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@Valid @RequestBody CreateInvoiceResource resource, Locale locale) {
        var command = CreateInvoiceCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = service.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(result,
                InvoiceResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED, messages, locale);
    }
}
