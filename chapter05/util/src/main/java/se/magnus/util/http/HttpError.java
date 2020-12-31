package se.magnus.util.http;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
public class HttpError {

    private final ZonedDateTime timestamp;
    private final String path;
    private final HttpStatus httpStatus;
    private final String message;

    public HttpError(String path, HttpStatus httpStatus, String message) {
        timestamp = ZonedDateTime.now();
        this.path = path;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpError() {
        timestamp = null;
        path = null;
        httpStatus = null;
        message = null;
    }

    public String getError() {
        return httpStatus.getReasonPhrase();
    }
}
