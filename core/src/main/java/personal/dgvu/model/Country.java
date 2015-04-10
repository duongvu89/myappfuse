package personal.dgvu.model;

import javax.persistence.*;


/**
 * Created by ndvu on 4/8/2015.
 */
@Entity
@Table(name = "country")
public class Country extends BaseObject {

    private String code;
    private String name;
    private String currency;

    @Id
    @Column(nullable = false, length = 2, updatable = false)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false, length = 3)
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        Country country = (Country) o;

        return code.equals(country.code);

    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public String toString() {
        return "Country{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }
}
