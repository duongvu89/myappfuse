package personal.dgvu.model;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * Created by Duong Vu on 04-Apr-15.
 */
@Entity
@Table(name = "salary_record")
public class SalaryRecord extends BaseObject implements Serializable {

    private Long id;
    private User user;
    private Date startDate;
    private Date endDate;
    private String company;
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

    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    @Column(name="salary")
    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    //don't have @Formula table has tax column
    @Formula("salary * 10")
    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = new BigDecimal(salary * 0.01);//this is the formula!?
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SalaryRecord that = (SalaryRecord) o;

        if (!id.equals(that.id)) return false;
        if (!user.equals(that.user)) return false;
        if (!startDate.equals(that.startDate)) return false;
        return !(endDate != null ? !endDate.equals(that.endDate) : that.endDate != null);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SalaryRecord{" +
                "id=" + id +
                ", user=" + user +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", company='" + company + '\'' +
                ", salary=" + salary +
                ", tax=" + tax +
                '}';
    }
}
