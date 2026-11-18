package pe.com.facturease.platform.u202422298.billing.interfaces.rest.transform;

import pe.com.facturease.platform.u202422298.billing.domain.model.commands.CreateInvoiceCommand;
import pe.com.facturease.platform.u202422298.billing.interfaces.rest.resources.CreateInvoiceResource;

/** Converts the HTTP create resource into the CQRS command.
 * @author Replace With Your Full Name
 */
public final class CreateInvoiceCommandFromResourceAssembler {
    private CreateInvoiceCommandFromResourceAssembler() { }
    public static CreateInvoiceCommand toCommandFromResource(CreateInvoiceResource resource) {
        return new CreateInvoiceCommand(resource.invoiceIdentifier(), resource.description(),
                resource.status(), resource.netAmount(), resource.calculatedTax(),
                resource.itemCount());
    }
}
