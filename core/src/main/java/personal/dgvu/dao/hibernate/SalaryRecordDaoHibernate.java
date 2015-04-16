package personal.dgvu.dao.hibernate;


import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import personal.dgvu.dao.SalaryRecordDao;
import personal.dgvu.model.SalaryRecord;
import personal.dgvu.model.TaxRate;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Duong Vu on 04-Apr-15.
 */
@Repository("salaryDao")
public class SalaryRecordDaoHibernate extends GenericDaoHibernate<SalaryRecord, Long> implements SalaryRecordDao{

    public SalaryRecordDaoHibernate() {
        super(SalaryRecord.class);
    }

    @Override
    public SalaryRecord get(Long id) {
        SalaryRecord record = super.get(id);

//        Query query = getSession().createQuery("from TaxRate");
//        List<TaxRate> allTaxRates = query.list();
//
//        List<TaxRate> taxRates = (List<TaxRate>) CollectionUtils.select(allTaxRates, new Predicate() {
//            @Override
//            public boolean evaluate(Object o) {
//                TaxRate taxRate = (TaxRate) o;
//                return taxRate.getCountry().getCode() == record.getCountry().getCode();
//            }
//        });
//
//        BigDecimal tax = new BigDecimal(0);
//        BigDecimal salary = new BigDecimal(record.getSalary());
//        for(TaxRate taxRate : taxRates) {
//            if (salary.compareTo(taxRate.getTo()) >= 0) {
//                tax =  tax.add(taxRate.getTo().subtract(taxRate.getFrom()).multiply(taxRate.getRate().divide(new BigDecimal(100))));
//            } else if (salary.compareTo(taxRate.getTo()) < 0 & salary.compareTo(taxRate.getFrom()) >= 0) {
//                tax =  tax.add(salary.subtract(taxRate.getFrom()).multiply(taxRate.getRate().divide(new BigDecimal(100))));
//            }
//        }

            record.setTax(new BigDecimal(1000));
//        record.setTax(tax);
//        System.out.println("*******" + tax);
        System.out.println("~~~~" + record.toString());
        return record;

    }

    @Override
    public List<SalaryRecord> getAll() {
        List<SalaryRecord> records = super.getAll();

//        Query query = getSession().createQuery("from TaxRate");
//
//        List<TaxRate> allTaxRates = query.list();

        for (int i = 0; i < records.size(); i++) {
//            final SalaryRecord record = records.get(i);
//            List<TaxRate> taxRates = (List<TaxRate>) CollectionUtils.select(allTaxRates, new Predicate() {
//                @Override
//                public boolean evaluate(Object o) {
//                    TaxRate taxRate = (TaxRate) o;
//                    return taxRate.getCountry().getCode() == record.getCountry().getCode();
//                }
//            });
//
//            BigDecimal tax = new BigDecimal(0);
//            BigDecimal salary = new BigDecimal(record.getSalary());
//            for(TaxRate taxRate : taxRates) {
//                if (salary.compareTo(taxRate.getTo()) >= 0) {
//                    tax =  tax.add(taxRate.getTo().subtract(taxRate.getFrom()).multiply(taxRate.getRate().divide(new BigDecimal(100))));
//                } else if (salary.compareTo(taxRate.getTo()) < 0 & salary.compareTo(taxRate.getFrom()) >= 0) {
//                    tax =  tax.add(salary.subtract(taxRate.getFrom()).multiply(taxRate.getRate().divide(new BigDecimal(100))));
//                }
//            }

            records.get(i).setTax(new BigDecimal(1000));
//            System.out.println("*******" + tax);
            System.out.println("~~~~" + records.toString());
        }
        return records;
    }
}
