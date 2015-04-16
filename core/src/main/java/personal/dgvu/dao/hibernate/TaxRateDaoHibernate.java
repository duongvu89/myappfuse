package personal.dgvu.dao.hibernate;

import org.springframework.stereotype.Repository;
import personal.dgvu.dao.TaxRateDao;
import personal.dgvu.model.TaxRate;

/**
 * Created by ndvu on 4/16/2015.
 */
@Repository("taxRateDao")
public class TaxRateDaoHibernate extends GenericDaoHibernate<TaxRate, Long> implements TaxRateDao {


    public TaxRateDaoHibernate() {
        super(TaxRate.class);
    }
}
