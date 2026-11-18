package pe.com.facturease.platform.u202422298.shared.domain.model.exceptions;

/** Shared domain exception for illegal business states with a localization key.
 * @author Replace With Your Full Name
 */
public class BusinessRuleViolationException extends IllegalStateException {
    private final String messageKey;

    public BusinessRuleViolationException(String messageKey, String message) {
        super(message);
        this.messageKey = messageKey;
    }

    public String messageKey() { return messageKey; }
}
