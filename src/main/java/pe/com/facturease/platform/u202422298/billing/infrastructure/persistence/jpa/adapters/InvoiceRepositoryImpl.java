package pe.com.facturease.platform.u202422298.billing.infrastructure.persistence.jpa.adapters;

import pe.com.facturease.platform.u202422298.billing.domain.model.aggregates.Invoice;
import pe.com.facturease.platform.u202422298.billing.domain.model.valueobjects.InvoiceIdentifier;
import pe.com.facturease.platform.u202422298.billing.domain.repositories.InvoiceRepository;
import pe.com.facturease.platform.u202422298.billing.infrastructure.persistence.jpa.assemblers.InvoicePersistenceAssembler;
import pe.com.facturease.platform.u202422298.billing.infrastructure.persistence.jpa.repositories.InvoicePersistenceRepository;
import org.springframework.stereotype.Repository;

/** Repository adapter connecting the domain repository to JPA.
 * @author Replace With Your Full Name
 */
@Repository
public class InvoiceRepositoryImpl implements InvoiceRepository {
    private final InvoicePersistenceRepository persistenceRepository;
    public InvoiceRepositoryImpl(InvoicePersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
    }

    @Override
    public boolean existsByInvoiceIdentifier(InvoiceIdentifier identifier) {
        return persistenceRepository.existsByInvoiceIdentifier(identifier.value());
    }

    @Override
    public Invoice save(Invoice invoice) {
        var entity = InvoicePersistenceAssembler.toPersistenceFromDomain(invoice);
        var saved = persistenceRepository.saveAndFlush(entity);
        return InvoicePersistenceAssembler.toDomainFromPersistence(saved);
    }
}
