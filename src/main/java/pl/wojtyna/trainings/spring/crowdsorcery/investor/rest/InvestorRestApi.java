package pl.wojtyna.trainings.spring.crowdsorcery.investor.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wojtyna.trainings.spring.crowdsorcery.investor.service.InvestorService;
import pl.wojtyna.trainings.spring.crowdsorcery.investor.service.RegisterInvestor;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/investorModule/api/v0/investors")
@Slf4j
public class InvestorRestApi {

    private final InvestorService investorService;

    public InvestorRestApi(InvestorService investorService) {
        this.investorService = investorService;
    }

    @PostMapping
    public ResponseEntity<RegisterInvestorErrorResponse> register(@RequestBody RegisterInvestorRestDto registerInvestorRestDto) {
        try {
            investorService.register(new RegisterInvestor(registerInvestorRestDto.id(),
                                                          registerInvestorRestDto.name()));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new RegisterInvestorErrorResponse(registerInvestorRestDto, e.getMessage()));
        }
        return ResponseEntity.created(URI.create("/investorModule/api/v0/investors/%s".formatted(
                                 registerInvestorRestDto.id())))
                             .build();
    }

    @GetMapping("/{id}")
    public Optional<InvestorFetchResultRestDto> fetch(@PathVariable("id") String id) {
        log.info("Get investor");
        return investorService.findAll()
                              .stream()
                              .filter(investor -> Objects.equals(investor.id(), id))
                              .map(investor -> new InvestorFetchResultRestDto(investor.id(), investor.name()))
                              .findAny();
    }

    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<GenericErrorResponse> controllerExceptionHandler(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(new GenericErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                                                            exception.getMessage()));
    }

    private record RegisterInvestorErrorResponse(RegisterInvestorRestDto command, String reason) {

    }

    private record GenericErrorResponse(String status, String reason) {}
}
