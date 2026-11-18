package pe.com.facturease.platform.u202422298.billing.interfaces.rest.transform;

import pe.com.facturease.platform.u202422298.billing.domain.model.aggregates.Invoice;
import pe.com.facturease.platform.u202422298.billing.interfaces.rest.resources.InvoiceResource;

/** Converts a saved Invoice aggregate into an HTTP response.
 * @author Replace With Your Full Name
 */
public final class InvoiceResourceFromEntityAssembler {
    private InvoiceResourceFromEntityAssembler() { }
    public static InvoiceResource toResourceFromEntity(Invoice invoice) {
        return new InvoiceResource(invoice.getId(),
                invoice.getInvoiceIdentifier().value().toString(),
                invoice.getDescription(), invoice.getStatus().name(),
                invoice.getTaxSummary().netAmount(),
                invoice.getTaxSummary().calculatedTax(),
                invoice.getItemCount());
    }
}
