package pe.com.facturease.platform.u202422298.billing.interfaces.rest.resources;

import java.math.BigDecimal;

/** HTTP response with the exact flat field names required by PC2.
 * @author Replace With Your Full Name
 */
public record InvoiceResource(Long id, String invoiceId, String description,
        String status, BigDecimal netAmountValue, BigDecimal calculatedTaxValue,
        Integer itemCount) { }
