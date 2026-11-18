package pe.com.facturease.platform.u202422298.billing.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

/** Flat JSON request used to create an invoice; no ID in request.
 * @author Replace With Your Full Name
 */
public record CreateInvoiceResource(
        @NotBlank
        @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$")
        @Schema(example = "f17f40be-a9d3-4e3c-9384-885ab050e54a")
        String invoiceIdentifier,
        String description,
        @Schema(description = "Defaults to DRAFT when absent", example = "ISSUED")
        String status,
        @NotNull BigDecimal netAmount,
        @NotNull BigDecimal calculatedTax,
        @NotNull Integer itemCount) { }
