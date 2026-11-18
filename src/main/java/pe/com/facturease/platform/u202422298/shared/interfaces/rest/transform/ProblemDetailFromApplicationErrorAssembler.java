package pe.com.facturease.platform.u202422298.shared.interfaces.rest.transform;

import pe.com.facturease.platform.u202422298.shared.application.result.ApplicationError;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import java.util.Locale;

/** Maps application failures to localized HTTP ProblemDetail responses.
 * @author Replace With Your Full Name
 */
public final class ProblemDetailFromApplicationErrorAssembler {
    private ProblemDetailFromApplicationErrorAssembler() {}

    public static ResponseEntity<ProblemDetail> toResponseFromApplicationError(
            ApplicationError error, MessageSource messages, Locale locale) {
        HttpStatus status = "CONFLICT".equals(error.code())
                ? HttpStatus.CONFLICT : HttpStatus.BAD_REQUEST;
        String titleKey = status == HttpStatus.CONFLICT
                ? "error.title.conflict" : "error.title.bad-request";
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status,
                messages.getMessage(error.messageKey(), null, locale));
        problem.setTitle(messages.getMessage(titleKey, null, locale));
        problem.setProperty("code", error.code());
        return ResponseEntity.status(status).body(problem);
    }
}
