package pe.com.facturease.platform.u202422298.billing.infrastructure.persistence.jpa.entities;

import pe.com.facturease.platform.u202422298.billing.domain.model.valueobjects.InvoiceStatus;
import pe.com.facturease.platform.u202422298.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

/** Persistence entity (not the domain aggregate).
 * Physical naming strategy maps Invoice to facturease_db.invoices.
 * @author Replace With Your Full Name
 */
@Entity
@Table(name = "Invoice", schema = "facturease_db")
public class InvoicePersistenceEntity extends AuditableAbstractPersistenceEntity {
    @Column(nullable = false, unique = true)
    private UUID invoiceIdentifier;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvoiceStatus status;

    @Column(nullable = false, columnDefinition = "numeric")
    private BigDecimal netAmount;

    @Column(nullable = false, columnDefinition = "numeric")
    private BigDecimal calculatedTax;

    @Column(nullable = false)
    private Integer itemCount;

    public InvoicePersistenceEntity() { }
    public UUID getInvoiceIdentifier() { return invoiceIdentifier; }
    public void setInvoiceIdentifier(UUID value) { invoiceIdentifier = value; }
    public String getDescription() { return description; }
    public void setDescription(String value) { description = value; }
    public InvoiceStatus getStatus() { return status; }
    public void setStatus(InvoiceStatus value) { status = value; }
    public BigDecimal getNetAmount() { return netAmount; }
    public void setNetAmount(BigDecimal value) { netAmount = value; }
    public BigDecimal getCalculatedTax() { return calculatedTax; }
    public void setCalculatedTax(BigDecimal value) { calculatedTax = value; }
    public Integer getItemCount() { return itemCount; }
    public void setItemCount(Integer value) { itemCount = value; }
}
