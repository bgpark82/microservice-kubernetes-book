package se.magnus.util.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import se.magnus.util.exception.InvalidInputException;
import se.magnus.util.exception.NotFoundException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public @ResponseBody HttpError handleNotFoundException(ServerHttpRequest request, Exception ex) {
        return getHttpError(request, ex, NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidInputException.class)
    public @ResponseBody HttpError handleInvalidInputException(ServerHttpRequest request, Exception ex) {
        return getHttpError(request, ex, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private HttpError getHttpError(ServerHttpRequest request, Exception ex, HttpStatus unprocessableEntity) {
        String path = request.getPath().pathWithinApplication().value();
        String message = ex.getMessage();

        LOG.debug("Retuning HTTP status: {} for path: {}, message {}", NOT_FOUND, path, message);
        return new HttpError(path, unprocessableEntity, message);
    }
}
