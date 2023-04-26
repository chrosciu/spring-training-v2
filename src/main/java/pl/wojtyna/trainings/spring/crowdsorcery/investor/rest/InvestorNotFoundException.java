package pl.wojtyna.trainings.spring.crowdsorcery.investor.rest;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class InvestorNotFoundException extends RuntimeException {
    String investorId;
}
