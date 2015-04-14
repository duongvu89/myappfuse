package personal.dgvu.dao.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import personal.dgvu.dao.SalaryRecordDao;
import personal.dgvu.model.SalaryRecord;
import personal.dgvu.model.TaxRate;
import personal.dgvu.model.User;

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
    public List<SalaryRecord> getAll() {
        List<SalaryRecord> records = super.getAll();
        for (int i = 0; i < records.size(); i++) {
            List<TaxRate> taxRates = getSession().createCriteria(TaxRate.class).add(Restrictions.eq("country_code", records.get(i).getCountry().getCode())).list();
            records.get(i).setTax(new BigDecimal(10));
            System.out.println("~~~~" + records.toString());
        }
        return records;
    }
}
