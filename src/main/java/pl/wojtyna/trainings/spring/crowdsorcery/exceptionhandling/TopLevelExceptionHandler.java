package pl.wojtyna.trainings.spring.crowdsorcery.exceptionhandling;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.wojtyna.trainings.spring.crowdsorcery.investor.rest.InvestorRestApi;

import java.util.Locale;

@ControllerAdvice
public class TopLevelExceptionHandler {
    private final MessageSource messageSource;

    public TopLevelExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<GenericErrorResponse> handleGenericException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(new GenericErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                                                            exception.getMessage()));
    }

    @ExceptionHandler(InvestorRestApi.NoInvestorFoundException.class)
    private ResponseEntity<GenericErrorResponse> handleNoInvestorFoundException(
            InvestorRestApi.NoInvestorFoundException exception, Locale locale) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new GenericErrorResponse(HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        messageSource.getMessage(InvestorRestApi.NoInvestorFoundException.class.getSimpleName(),
                                new Object[]{exception.getId()}, locale)));
    }

    private record GenericErrorResponse(String status, String reason) {}
}
