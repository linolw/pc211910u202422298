package pe.com.facturease.platform.u202422298.billing.domain.model.exceptions;

import pe.com.facturease.platform.u202422298.shared.domain.model.exceptions.BusinessRuleViolationException;

/** Illegal state for issuing or paying an empty invoice.
 * @author Replace With Your Full Name
 */
public class InvalidInvoiceItemCountException extends BusinessRuleViolationException {
    public InvalidInvoiceItemCountException() {
        super("error.invoice.items", "ISSUED and PAID invoices require at least one item.");
    }
}
