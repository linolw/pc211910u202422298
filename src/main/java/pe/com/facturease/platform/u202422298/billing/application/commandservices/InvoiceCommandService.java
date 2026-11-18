package pe.com.facturease.platform.u202422298.billing.application.commandservices;

import pe.com.facturease.platform.u202422298.billing.domain.model.aggregates.Invoice;
import pe.com.facturease.platform.u202422298.billing.domain.model.commands.CreateInvoiceCommand;
import pe.com.facturease.platform.u202422298.shared.application.result.ApplicationError;
import pe.com.facturease.platform.u202422298.shared.application.result.Result;

/** Use-case contract for invoice commands.
 * @author Replace With Your Full Name
 */
public interface InvoiceCommandService {
    Result<Invoice, ApplicationError> handle(CreateInvoiceCommand command);
}
