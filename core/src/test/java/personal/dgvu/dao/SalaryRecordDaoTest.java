package personal.dgvu.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.*;
/**
 * Created by Duong Vu on 05-Apr-15.
 */
public class SalaryRecordDaoTest extends BaseDaoTestCase {
    @Autowired
    private SalaryRecordDao srDao;

    @Test
    public void testExits() throws Exception {
        assertTrue(srDao.exists(-1L));
        assertEquals(srDao.getAll().size(), 3L);
        assertEquals(srDao.getAllDistinct().size(), 3L);
    }
}
