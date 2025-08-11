package javaDaddy.ToDo.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class ValidationErrorResponse extends ErrorResponse {

    private Map<String, String> fieldErrors = new HashMap<>();

    public ValidationErrorResponse(LocalDateTime timestamp, int status, String error, String message) {
        super(timestamp, status, error, message);
    }

    public void addFieldError(String field, String message) {
        this.fieldErrors.put(field, message);
    }
}
