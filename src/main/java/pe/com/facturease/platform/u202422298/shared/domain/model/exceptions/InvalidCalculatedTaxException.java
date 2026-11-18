package pe.com.facturease.platform.u202422298.shared.domain.model.exceptions;

/** Illegal argument error for a tax amount inconsistent with 18 percent VAT.
 * @author Replace With Your Full Name
 */
public class InvalidCalculatedTaxException extends IllegalArgumentException {
    public InvalidCalculatedTaxException() {
        super("Calculated tax must equal 18 percent of net amount.");
    }
}
