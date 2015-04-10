package personal.dgvu.dao;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ndvu on 4/10/2015.
 */
public class CountryDaoTest extends BaseDaoTestCase {
    @Autowired
    private CountryDao dao;

    @Test
    public void testExits() throws Exception {
        Assert.assertTrue(this.dao.exists("VN"));
        Assert.assertTrue(this.dao.exists("MS"));
        Assert.assertEquals(this.dao.getAll().size(), 2);
        Assert.assertEquals(this.dao.getAllDistinct().size(), 2);
    }
}
