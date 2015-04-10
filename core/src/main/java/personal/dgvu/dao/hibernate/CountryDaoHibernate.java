package personal.dgvu.dao.hibernate;

import org.springframework.stereotype.Repository;
import personal.dgvu.dao.CountryDao;
import personal.dgvu.model.Country;

/**
 * Created by Duong Vu on 09-Apr-15.
 */
@Repository
public class CountryDaoHibernate extends GenericDaoHibernate<Country, String> implements CountryDao {
    public CountryDaoHibernate() {
        super(Country.class);
    }
}
