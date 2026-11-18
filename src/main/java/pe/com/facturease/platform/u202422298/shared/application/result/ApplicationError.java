package pe.com.facturease.platform.u202422298.shared.application.result;

/** Application-layer error independent of the HTTP transport.
 * @param code machine-readable error category
 * @param messageKey resource-bundle key used by REST translation
 * @author Replace With Your Full Name
 */
public record ApplicationError(String code, String messageKey) {
    public static ApplicationError conflict(String messageKey) {
        return new ApplicationError("CONFLICT", messageKey);
    }
}
