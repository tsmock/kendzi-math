package kendzi.math.geometry;

import javax.vecmath.Point2d;
import javax.vecmath.Vector2d;

import kendzi.math.geometry.skeleton.Skeleton;
import kendzi.math.geometry.skeleton.debug.EmptyDebugger;
import kendzi.math.geometry.skeleton.debug.VisualDebugger;

/**
 * import static kendzi.math.geometry.TestUtil.*;
 *
 * @author Tomasz Kedziora (Kendzi)
 *
 */
public class TestUtil {
    public static Vector2d v(double x, double y) {
        return new Vector2d(x, y);
    }

    public static Point2d p(double x, double y) {
        return new Point2d(x, y);
    }

    public static VisualDebugger initVisualDebugger() {
        if (System.getProperty("ui") != null) {
            // FIXME
            return Skeleton.initVisualDebugger();
        }
        return new EmptyDebugger();
    }
}
