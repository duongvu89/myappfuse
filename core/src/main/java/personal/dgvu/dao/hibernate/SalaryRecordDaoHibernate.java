package personal.dgvu.dao.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import personal.dgvu.dao.SalaryRecordDao;
import personal.dgvu.model.SalaryRecord;

/**
 * Created by Duong Vu on 04-Apr-15.
 */
@Repository("salaryDao")
public class SalaryRecordDaoHibernate extends GenericDaoHibernate<SalaryRecord, Long> implements SalaryRecordDao{

    public SalaryRecordDaoHibernate() {
        super(SalaryRecord.class);
    }
}
