package pe.com.facturease.platform.u202422298.shared.application.result;

/** Explicit success/failure return type used by command services.
 * @param <T> successful value type
 * @param <E> failure type
 * @author Replace With Your Full Name
 */
public sealed interface Result<T, E> {
    record Success<T, E>(T value) implements Result<T, E> {}
    record Failure<T, E>(E error) implements Result<T, E> {}

    static <T, E> Result<T, E> success(T value) {
        return new Success<>(value);
    }

    static <T, E> Result<T, E> failure(E error) {
        return new Failure<>(error);
    }
}
