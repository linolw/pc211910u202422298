package pe.com.facturease.platform.u202422298.shared.interfaces.rest.transform;

import pe.com.facturease.platform.u202422298.shared.application.result.ApplicationError;
import pe.com.facturease.platform.u202422298.shared.application.result.Result;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Locale;
import java.util.function.Function;

/** Converts a CQRS command result to an HTTP response.
 * @author Replace With Your Full Name
 */
public final class ResponseEntityAssembler {
    private ResponseEntityAssembler() {}

    public static <T, R> ResponseEntity<?> toResponseEntityFromResult(
            Result<T, ApplicationError> result,
            Function<T, R> successMapper,
            HttpStatus successStatus,
            MessageSource messages,
            Locale locale) {
        return switch (result) {
            case Result.Success<T, ApplicationError> success ->
                    ResponseEntity.status(successStatus)
                            .body(successMapper.apply(success.value()));
            case Result.Failure<T, ApplicationError> failure ->
                    ProblemDetailFromApplicationErrorAssembler
                            .toResponseFromApplicationError(failure.error(), messages, locale);
        };
    }
}
