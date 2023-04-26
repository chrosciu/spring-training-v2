package pl.wojtyna.trainings.spring.crowdsorcery.investor.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wojtyna.trainings.spring.crowdsorcery.investor.service.InvestorService;
import pl.wojtyna.trainings.spring.crowdsorcery.investor.service.RegisterInvestor;

import javax.validation.Valid;
import java.net.URI;
import java.util.Locale;
import java.util.Objects;

@RestController
@RequestMapping("/investorModule/api/v0/investors")
@Slf4j
public class InvestorRestApi {

    private final InvestorService investorService;
    private final MessageSource messageSource;

    public InvestorRestApi(InvestorService investorService, MessageSource messageSource) {
        this.investorService = investorService;
        this.messageSource = messageSource;
    }

    @PostMapping
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterInvestorRestDto registerInvestorRestDto) {
        investorService.register(new RegisterInvestor(registerInvestorRestDto.id(),
                registerInvestorRestDto.name()));
        return ResponseEntity.created(URI.create("/investorModule/api/v0/investors/%s".formatted(
                        registerInvestorRestDto.id())))
                .build();
    }

    @GetMapping("/{id}")
    public InvestorFetchResultRestDto fetch(@PathVariable("id") String id) {
        log.info("Get investor");
        return investorService.findAll()
                .stream()
                .filter(investor -> Objects.equals(investor.id(), id))
                .map(investor -> new InvestorFetchResultRestDto(investor.id(), investor.name()))
                .findAny()
                .orElseThrow(() -> new InvestorNotFoundException(id));
    }

    @ExceptionHandler
    public ResponseEntity<Void> handleInvestorNotFoundException(InvestorNotFoundException e, Locale locale) {
        var messageCode = e.getClass().getSimpleName();
        var message = messageSource.getMessage(
                messageCode, new Object[]{e.getInvestorId()}, locale);
        return ResponseEntity.noContent()
                .header("X-Message", message)
                .build();
    }
}
