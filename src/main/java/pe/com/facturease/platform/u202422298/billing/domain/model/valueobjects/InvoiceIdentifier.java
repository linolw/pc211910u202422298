package pe.com.facturease.platform.u202422298.billing.domain.model.valueobjects;

import java.util.Objects;
import java.util.UUID;

/** Domain value object identifying an invoice by external UUID.
 * @author Replace With Your Full Name
 */
public record InvoiceIdentifier(UUID value) {
    public InvoiceIdentifier { Objects.requireNonNull(value, "Invoice identifier is required."); }
}
