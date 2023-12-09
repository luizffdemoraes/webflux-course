package br.com.luizffdemoraes.webfluxcourse.controller.exceptions;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationError extends StandardError implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<FieldError> errors = new ArrayList<>();

    public ValidationError(LocalDateTime timestamp, int status, String error, String message, String path) {
        super(timestamp, status, error, message, path);
    }

    public void addError(String fieldName, String message) {
        this.errors.add(new FieldError(fieldName, message));
    }

    @Getter
    private static final class FieldError {
        private String fieldName;
        private String message;

        public FieldError(String fieldName, String message) {
            this.fieldName = fieldName;
            this.message = message;
        }
    }
}
