package pe.com.facturease.platform.u202422298.billing.domain.model.aggregates;

import pe.com.facturease.platform.u202422298.billing.domain.model.exceptions.InvalidInvoiceItemCountException;
import pe.com.facturease.platform.u202422298.billing.domain.model.valueobjects.InvoiceIdentifier;
import pe.com.facturease.platform.u202422298.billing.domain.model.valueobjects.InvoiceStatus;
import pe.com.facturease.platform.u202422298.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import pe.com.facturease.platform.u202422298.shared.domain.model.valueobjects.TaxSummary;
import java.util.Objects;

/** Aggregate Root for a corporate electronic invoice.
 * @author Replace With Your Full Name
 */
public class Invoice extends AbstractDomainAggregateRoot<Invoice> {
    private final Long id;
    private final InvoiceIdentifier invoiceIdentifier;
    private final String description;
    private final InvoiceStatus status;
    private final TaxSummary taxSummary;
    private final Integer itemCount;

    /** Creates a new invoice without its database-generated identifier. */
    public Invoice(InvoiceIdentifier invoiceIdentifier, String description,
                   InvoiceStatus status, TaxSummary taxSummary, Integer itemCount) {
        this(null, invoiceIdentifier, description, status, taxSummary, itemCount);
    }

    private Invoice(Long id, InvoiceIdentifier invoiceIdentifier, String description,
                    InvoiceStatus status, TaxSummary taxSummary, Integer itemCount) {
        this.id = id;
        this.invoiceIdentifier = Objects.requireNonNull(invoiceIdentifier, "Invoice identifier is required.");
        this.description = description;
        this.status = status == null ? InvoiceStatus.DRAFT : status;
        this.taxSummary = Objects.requireNonNull(taxSummary, "Tax summary is required.");
        if (itemCount == null) throw new IllegalArgumentException("Item count is required.");
        if (this.status != InvoiceStatus.DRAFT && itemCount <= 0) {
            throw new InvalidInvoiceItemCountException();
        }
        this.itemCount = itemCount;
    }

    /** Rebuilds an aggregate from its persistence representation.
     * @return a populated Invoice aggregate
     */
    public static Invoice reconstitute(Long id, InvoiceIdentifier identifier, String description,
                                       InvoiceStatus status, TaxSummary taxSummary, Integer itemCount) {
        return new Invoice(Objects.requireNonNull(id, "Persisted id is required."),
                identifier, description, status, taxSummary, itemCount);
    }

    public Long getId() { return id; }
    public InvoiceIdentifier getInvoiceIdentifier() { return invoiceIdentifier; }
    public String getDescription() { return description; }
    public InvoiceStatus getStatus() { return status; }
    public TaxSummary getTaxSummary() { return taxSummary; }
    public Integer getItemCount() { return itemCount; }
}
