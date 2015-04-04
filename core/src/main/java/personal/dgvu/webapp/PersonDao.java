package personal.dgvu.webapp;

import personal.dgvu.dao.GenericDao;
import personal.dgvu.webapp.model.Person;

import java.util.List;

/**
 * Created by ndvu on 3/30/2015.
 */
public interface PersonDao extends GenericDao<Person, Long> {

    public List<Person> findByLastName(String lastName);
}