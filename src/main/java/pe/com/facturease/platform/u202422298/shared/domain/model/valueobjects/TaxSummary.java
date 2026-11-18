package pe.com.facturease.platform.u202422298.shared.domain.model.valueobjects;

import pe.com.facturease.platform.u202422298.shared.domain.model.exceptions.InvalidCalculatedTaxException;
import java.math.BigDecimal;

/**
 * Immutable tax summary; calculatedTax must equal 18% of netAmount.
 * The statement does not define rounding, so the check uses exact decimal arithmetic.
 * @author Replace With Your Full Name
 */
public record TaxSummary(BigDecimal netAmount, BigDecimal calculatedTax) {
    public TaxSummary {
        if (netAmount == null || calculatedTax == null) {
            throw new IllegalArgumentException("Net amount and calculated tax are required.");
        }
        BigDecimal expected = netAmount.multiply(new BigDecimal("0.18"));
        if (expected.compareTo(calculatedTax) != 0) {
            throw new InvalidCalculatedTaxException();
        }
    }
}
