package personal.dgvu.dao.hibernate;

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
    public List<SalaryRecord> getAll() {
        List<SalaryRecord> records = super.getAll();

        Query query = getSession().createQuery("from TaxRate");
//
//        List<TaxRate> taxRates = query.list();

        for (int i = 0; i < records.size(); i++) {

            Criteria cr = getSession().createCriteria(TaxRate.class);
            cr.add(Restrictions.eq("country", records.get(i).getCountry().getCode()));
            List<TaxRate> results = cr.list();
            records.get(i).setTax(new BigDecimal(10));
            System.out.println("*******" + results);
            System.out.println("~~~~" + records.toString());
        }
        return records;
    }
}
