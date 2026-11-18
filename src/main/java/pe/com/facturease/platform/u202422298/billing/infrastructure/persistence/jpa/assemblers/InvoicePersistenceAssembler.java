package pe.com.facturease.platform.u202422298.billing.infrastructure.persistence.jpa.assemblers;

import pe.com.facturease.platform.u202422298.billing.domain.model.aggregates.Invoice;
import pe.com.facturease.platform.u202422298.billing.domain.model.valueobjects.InvoiceIdentifier;
import pe.com.facturease.platform.u202422298.billing.infrastructure.persistence.jpa.entities.InvoicePersistenceEntity;
import pe.com.facturease.platform.u202422298.shared.domain.model.valueobjects.TaxSummary;

/** Maps invoice aggregate to and from its JPA representation.
 * @author Replace With Your Full Name
 */
public final class InvoicePersistenceAssembler {
    private InvoicePersistenceAssembler() { }
    public static InvoicePersistenceEntity toPersistenceFromDomain(Invoice domain) {
        var entity = new InvoicePersistenceEntity();
        entity.setId(domain.getId());
        entity.setInvoiceIdentifier(domain.getInvoiceIdentifier().value());
        entity.setDescription(domain.getDescription());
        entity.setStatus(domain.getStatus());
        entity.setNetAmount(domain.getTaxSummary().netAmount());
        entity.setCalculatedTax(domain.getTaxSummary().calculatedTax());
        entity.setItemCount(domain.getItemCount());
        return entity;
    }

    public static Invoice toDomainFromPersistence(InvoicePersistenceEntity entity) {
        return Invoice.reconstitute(entity.getId(),
                new InvoiceIdentifier(entity.getInvoiceIdentifier()),
                entity.getDescription(), entity.getStatus(),
                new TaxSummary(entity.getNetAmount(), entity.getCalculatedTax()),
                entity.getItemCount());
    }
}
