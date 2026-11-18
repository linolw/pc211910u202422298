package pe.com.facturease.platform.u202422298.billing.application.internal.commandservices;

import pe.com.facturease.platform.u202422298.billing.application.commandservices.InvoiceCommandService;
import pe.com.facturease.platform.u202422298.billing.domain.model.aggregates.Invoice;
import pe.com.facturease.platform.u202422298.billing.domain.model.commands.CreateInvoiceCommand;
import pe.com.facturease.platform.u202422298.billing.domain.model.valueobjects.InvoiceIdentifier;
import pe.com.facturease.platform.u202422298.billing.domain.model.valueobjects.InvoiceStatus;
import pe.com.facturease.platform.u202422298.billing.domain.repositories.InvoiceRepository;
import pe.com.facturease.platform.u202422298.shared.application.result.ApplicationError;
import pe.com.facturease.platform.u202422298.shared.application.result.Result;
import pe.com.facturease.platform.u202422298.shared.domain.model.valueobjects.TaxSummary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

/** Application use case: enforce uniqueness and persist a valid invoice.
 * @author Replace With Your Full Name
 */
@Service
public class InvoiceCommandServiceImpl implements InvoiceCommandService {
    private final InvoiceRepository repository;
    public InvoiceCommandServiceImpl(InvoiceRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Result<Invoice, ApplicationError> handle(CreateInvoiceCommand command) {
        if (command == null) throw new IllegalArgumentException("Command is required.");

        InvoiceIdentifier identifier = new InvoiceIdentifier(
                UUID.fromString(command.invoiceIdentifier()));
        if (repository.existsByInvoiceIdentifier(identifier)) {
            return Result.failure(ApplicationError.conflict("invoice.duplicate"));
        }
        InvoiceStatus status = InvoiceStatus.fromString(command.status());
        TaxSummary tax = new TaxSummary(command.netAmount(), command.calculatedTax());
        Invoice invoice = new Invoice(identifier, command.description(), status,
                tax, command.itemCount());
        return Result.success(repository.save(invoice));
    }
}
