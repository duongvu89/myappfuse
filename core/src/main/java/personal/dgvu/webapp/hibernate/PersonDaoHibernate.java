package personal.dgvu.webapp.hibernate;


import personal.dgvu.dao.hibernate.GenericDaoHibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import personal.dgvu.webapp.PersonDao;
import personal.dgvu.webapp.model.Person;

import java.util.List;

/**
 * Created by ndvu on 3/30/2015.
 */
@Repository("personDao")
public class PersonDaoHibernate extends GenericDaoHibernate<Person, Long> implements PersonDao {

    public PersonDaoHibernate() {
        super(Person.class);
    }

    public List<Person> findByLastName(String lastName) {
        return getSession().createCriteria(Person.class).add(Restrictions.eq("lastName", lastName)).list();
    }
}