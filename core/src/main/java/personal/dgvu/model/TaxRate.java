package personal.dgvu.model;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by ndvu on 4/13/2015.
 */
@Entity
@Table(name = "tax_rate")
public class TaxRate extends BaseObject {

    private Long id;
    private Country country;
    private BigDecimal from;
    private BigDecimal to;
    private BigDecimal rate;
    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "country_code", unique = false, nullable = false)
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Column(name = "salary_from")
    public BigDecimal getFrom() {
        return from;
    }

    public void setFrom(BigDecimal from) {
        this.from = from;
    }

    @Column(name = "salary_to")
    public BigDecimal getTo() {
        return to;
    }

    public void setTo(BigDecimal to) {
        this.to = to;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaxRate taxRate = (TaxRate) o;

        if (!getCountry().equals(taxRate.getCountry())) return false;
        if (!getFrom().equals(taxRate.getFrom())) return false;
        return getTo().equals(taxRate.getTo());

    }

    @Override
    public int hashCode() {
        int result = getCountry().hashCode();
        result = 31 * result + getFrom().hashCode();
        result = 31 * result + getTo().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TaxRate{" +
                "id=" + id +
                ", country=" + country +
                ", from=" + from +
                ", to=" + to +
                ", rate=" + rate +
                '}';
    }
}
