package pe.com.facturease.platform.u202422298.billing.domain.model.valueobjects;

/** Allowed invoice statuses; DRAFT is the default.
 * @author Replace With Your Full Name
 */
public enum InvoiceStatus {
    DRAFT, ISSUED, PAID;
    public static InvoiceStatus fromString(String text) {
        if (text == null || text.isBlank()) return DRAFT;
        try { return InvoiceStatus.valueOf(text); }
        catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Unsupported invoice status: " + text, ex);
        }
    }
}
