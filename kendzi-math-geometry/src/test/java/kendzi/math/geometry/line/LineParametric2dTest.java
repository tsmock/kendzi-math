package kendzi.math.geometry.line;

import static kendzi.math.geometry.TestUtil.p;
import static kendzi.math.geometry.TestUtil.v;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LineParametric2dTest {

    @Test public void test_1() {

        LineParametric2d l = new LineParametric2d(p(0, 0), v(-1, -1));

        assertTrue(l.isOnLeftSite(p(0, -1), 0.1));
        assertTrue(l.isOnRightSite(p(-1, 0), 0.1));

    }

    @Test public void test_2() {

        LineParametric2d l = new LineParametric2d(p(1, 1), v(1, 1));

        assertTrue(l.isOnLeftSite(p(0, 1), 0.1));
        assertTrue(l.isOnRightSite(p(1, 0), 0.1));

    }

    @Test public void test_3() {

        LineParametric2d l = new LineParametric2d(p(-3, 1), v(1, -1));

        assertTrue(l.isOnLeftSite(p(-2, -0), 1E-10));
        assertTrue(l.isOnRightSite(p(-2, -0), 1E-10));

    }
}
