package pe.com.facturease.platform.u202422298.shared.interfaces.rest;

import pe.com.facturease.platform.u202422298.shared.domain.model.exceptions.BusinessRuleViolationException;
import pe.com.facturease.platform.u202422298.shared.domain.model.exceptions.InvalidCalculatedTaxException;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Locale;

/** Converts errors to localized HTTP problem-details responses.
 * @author Replace With Your Full Name
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final MessageSource messages;
    public GlobalExceptionHandler(MessageSource messages) { this.messages = messages; }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidation(MethodArgumentNotValidException ex, Locale locale) {
        var fields = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getField).distinct().toList();
        var response = problem(HttpStatus.BAD_REQUEST, "error.validation", "VALIDATION_ERROR", locale);
        response.getBody().setProperty("fields", fields);
        return response;
    }

    @ExceptionHandler(InvalidCalculatedTaxException.class)
    public ResponseEntity<ProblemDetail> handleTax(InvalidCalculatedTaxException ex, Locale locale) {
        return problem(HttpStatus.BAD_REQUEST, "error.tax.invalid", "INVALID_CALCULATED_TAX", locale);
    }

    @ExceptionHandler(BusinessRuleViolationException.class)
    public ResponseEntity<ProblemDetail> handleRule(BusinessRuleViolationException ex, Locale locale) {
        return problem(HttpStatus.UNPROCESSABLE_ENTITY, ex.messageKey(), "BUSINESS_RULE_VIOLATION", locale);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ProblemDetail> handleConstraint(DataIntegrityViolationException ex, Locale locale) {
        return problem(HttpStatus.CONFLICT, "invoice.duplicate", "CONFLICT", locale);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDetail> handleJson(HttpMessageNotReadableException ex, Locale locale) {
        return problem(HttpStatus.BAD_REQUEST, "error.invalid.json", "INVALID_REQUEST", locale);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> handleArgument(IllegalArgumentException ex, Locale locale) {
        return problem(HttpStatus.BAD_REQUEST, "error.invalid.argument", "INVALID_ARGUMENT", locale);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleUnexpected(Exception ex, Locale locale) {
        return problem(HttpStatus.INTERNAL_SERVER_ERROR, "error.server", "UNEXPECTED_ERROR", locale);
    }

    private ResponseEntity<ProblemDetail> problem(HttpStatus status, String key, String code, Locale locale) {
        var detail = ProblemDetail.forStatusAndDetail(status,
                messages.getMessage(key, null, locale));
        String titleKey = switch (status) {
            case BAD_REQUEST -> "error.title.bad-request";
            case CONFLICT -> "error.title.conflict";
            case UNPROCESSABLE_ENTITY -> "error.title.unprocessable-entity";
            default -> "error.title.internal-server-error";
        };
        detail.setTitle(messages.getMessage(titleKey, null, locale));
        detail.setProperty("code", code);
        return ResponseEntity.status(status).body(detail);
    }
}
