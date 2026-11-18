package pe.com.facturease.platform.u202422298.billing.domain.model.commands;

import java.math.BigDecimal;

/** CQRS command representing the intention to create an invoice.
 * @author Replace With Your Full Name
 */
public record CreateInvoiceCommand(
        String invoiceIdentifier,
        String description,
        String status,
        BigDecimal netAmount,
        BigDecimal calculatedTax,
        Integer itemCount) { }
