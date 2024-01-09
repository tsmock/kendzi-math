package kendzi.math.geometry.polygon.split;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point2d;

import kendzi.math.geometry.line.LinePoints2d;
import kendzi.math.geometry.line.LineUtil;

/**
 * Enrich polygonal chain by new points.
 *
 * @author Tomasz Kędziora (kendzi)
 *
 */
public class EnrichPolygonalChainUtil {

    private static final double EPSILON = 1E-10;

    private static final double ZERO = 0;

    /**
     * Splits the open polygonal chain by the line. Polygonal chain is enriched
     * by new points in the places where line cross chain.
     *
     * @param splittingLine
     *            the line which splitting polygonal chain
     * @param openPolygonalChain
     *            the open polygonal chain
     *
     * @return open polygonal chain enriched with extra points where line is
     *         crossing original chain
     */
    public static List<Point2d> enrichOpenPolygonalChainByLineCrossing(List<Point2d> openPolygonalChain,
            LinePoints2d splittingLine) {
        return enrichPolygonalChainByLineCrossing(openPolygonalChain, splittingLine, EPSILON, true);
    }

    /**
     * Splits the closed polygonal chain by the line. Polygonal chain is
     * enriched by new points in the places where line cross chain.
     *
     * @param splittingLine
     *            the line which splitting polygonal chain
     * @param openPolygonalChain
     *            the closed polygonal chain. Chain is assumed to be closed and
     *            don't have repeated first point at the begin and end of chain.
     *
     *
     * @return closed polygonal chain enriched with extra points where line is
     *         crossing original chain
     */
    public static List<Point2d> enrichClosedPolygonalChainByLineCrossing(List<Point2d> openPolygonalChain,
            LinePoints2d splittingLine) {
        return enrichPolygonalChainByLineCrossing(openPolygonalChain, splittingLine, EPSILON, false);
    }

    /**
     * Splits the polygonal chain by the line. Polygonal chain is enriched by
     * new points in the places where line cross chain.
     *
     * @param splittingLine
     *            the line which splitting polygonal chain
     * @param openPolygonalChain
     *            the polygonal chain. Chain is assumed to be closed and don't
     *            have repeated first point at the begin and end of chain.
     * @param epsilon
     *            an error epsilon used in calculation to deal with numerical
     *            errors
     * @param open
     *            if polygonal chain is open
     *
     * @return polygonal chain enriched with extra points where line is crossing
     *         original chain
     */
    public static List<Point2d> enrichPolygonalChainByLineCrossing(List<Point2d> closedPolygonalChain,
            LinePoints2d splittingLine, double epsilon, boolean open) {

        int chainSize = closedPolygonalChain.size();

        List<Point2d> enrichedPolygonalChain = new ArrayList<Point2d>(chainSize + 5);

        List<java.lang.Double> detList = new ArrayList<java.lang.Double>();
        for (Point2d point2d : closedPolygonalChain) {
            double matrixDet = LineUtil.matrixDet(splittingLine.getP1(), splittingLine.getP2(), point2d);
            if (equalZero(matrixDet, epsilon)) {
                /*
                 * Check if error is too small if so assign zero as determinant.
                 * This can be later check by reference equal.
                 */
                matrixDet = ZERO;
            }
            detList.add(matrixDet);
        }

        /*
         * for open chain we need to loop over one element less the in closed.
         * It is assumed that closed chain don't repeate first point at begin
         * and end of chain.
         */
        int loopSize = chainSize + (open ? -1 : 0);
        /*
         * We need to add new vertex on the places where line is crossing
         * polygonal chain.
         */
        for (int i = 0; i < loopSize; i++) {
            Point2d segmentBegin = closedPolygonalChain.get(i);
            Point2d segmentEnd = closedPolygonalChain.get((i + 1) % chainSize);

            double beginDet = detList.get(i);
            double endDet = detList.get((i + 1) % chainSize);

            enrichedPolygonalChain.add(segmentBegin);

            if (beginDet == ZERO || endDet == ZERO) {
                /*
                 * Begin point or end point is lies on splitting line already,
                 * we don't need add new point.
                 */
                continue;

            } else if (beginDet * endDet < 0) {
                /*
                 * Check if line segment is crossing the splitting line. If so
                 * we need to add new point to polygonal chain at the place
                 * where line segment is crossing the splitting line.
                 */
                Point2d crossingPoint = LineUtil.crossLineWithLineSegment(splittingLine.getP1(), splittingLine.getP2(),
                        segmentBegin, segmentEnd);

                enrichedPolygonalChain.add(crossingPoint);
            }
        }

        if (open) {
            /* Add the last point for open chain. */
            enrichedPolygonalChain.add(closedPolygonalChain.get(chainSize - 1));
        }

        return enrichedPolygonalChain;
    }

    private static boolean equalZero(double number, double epsilon) {
        return number * number < epsilon;
    }
}
