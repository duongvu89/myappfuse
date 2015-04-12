package personal.dgvu.model;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * Created by Duong Vu on 04-Apr-15.
 */
@Entity
@Table(name = "salary_record")
public class SalaryRecord extends BaseObject implements Comparable<SalaryRecord> {

    private Long id;
    private Date startDate;
    private Date endDate;
    private String company;
    private Country country;
    private int salary;
    private BigDecimal tax;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "start_date", nullable = false)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Column(name = "end_date")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @ManyToOne(optional=false)
    @JoinColumn(name = "country_code", unique=false, nullable=false, updatable=false)
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Formula("salary * 0.1")
    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SalaryRecord that = (SalaryRecord) o;

        if (getSalary() != that.getSalary()) return false;
        if (!getStartDate().equals(that.getStartDate())) return false;
        if (getEndDate() != null ? !getEndDate().equals(that.getEndDate()) : that.getEndDate() != null) return false;
        if (getCompany() != null ? !getCompany().equals(that.getCompany()) : that.getCompany() != null) return false;
        if (!getCountry().equals(that.getCountry())) return false;
        return !(getTax() != null ? !getTax().equals(that.getTax()) : that.getTax() != null);

    }

    @Override
    public int hashCode() {
        int result = getStartDate().hashCode();
        result = 31 * result + (getEndDate() != null ? getEndDate().hashCode() : 0);
        result = 31 * result + (getCompany() != null ? getCompany().hashCode() : 0);
        result = 31 * result + getCountry().hashCode();
        result = 31 * result + getSalary();
        result = 31 * result + (getTax() != null ? getTax().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SalaryRecord{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", company='" + company + '\'' +
                ", country=" + country +
                ", salary=" + salary +
                ", tax=" + tax +
                '}';
    }

    @Override
    public int compareTo(SalaryRecord o) {
        return this.startDate.compareTo(o.startDate);
    }
}
