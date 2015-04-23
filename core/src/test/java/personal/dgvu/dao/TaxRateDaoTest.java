package personal.dgvu.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import personal.dgvu.model.TaxRate;

import static org.junit.Assert.*;

/**
 * Created by ndvu on 4/23/2015.
 */
public class TaxRateDaoTest extends BaseDaoTestCase {
    @Autowired
    private TaxRateDao dao;

    @Test
    public void testTaxRateDao() {
        assertEquals(dao.getAllDistinct().size(), 12);

        dao.remove(dao.getAllDistinct().get(0));
        assertEquals(dao.getAllDistinct().size(), 11);

        TaxRate taxRate = dao.getAllDistinct().get(0);
        taxRate.setYear((short) 2016);
        dao.save(taxRate);
        flush();
        assertEquals(dao.getAllDistinct().size(), 12);
    }
}
