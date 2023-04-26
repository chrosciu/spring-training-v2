package pl.wojtyna.trainings.spring.crowdsorcery.investor.repository.jpa;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Data
@EqualsAndHashCode(of = "id")
@ToString(exclude = "investor")
public class InvestorProfileEntity {

    @Id
    private String id;
    private int score;
    private boolean isVip;
    private String referralLink;
    @OneToOne(mappedBy = "investorProfile")
    private InvestorEntity investor;
}
