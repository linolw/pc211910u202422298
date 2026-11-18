package pe.com.facturease.platform.u202422298.billing.domain.repositories;

import pe.com.facturease.platform.u202422298.billing.domain.model.aggregates.Invoice;
import pe.com.facturease.platform.u202422298.billing.domain.model.valueobjects.InvoiceIdentifier;

/** Domain repository port; not coupled to Spring Data JPA.
 * @author Replace With Your Full Name
 */
public interface InvoiceRepository {
    boolean existsByInvoiceIdentifier(InvoiceIdentifier identifier);
    Invoice save(Invoice invoice);
}
