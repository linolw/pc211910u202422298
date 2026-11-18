package pe.com.facturease.platform.u202422298.billing.infrastructure.persistence.jpa.repositories;

import pe.com.facturease.platform.u202422298.billing.infrastructure.persistence.jpa.entities.InvoicePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

/** Spring Data JPA repository for invoice rows.
 * @author Replace With Your Full Name
 */
public interface InvoicePersistenceRepository extends JpaRepository<InvoicePersistenceEntity, Long> {
    boolean existsByInvoiceIdentifier(UUID identifier);
}
