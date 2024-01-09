package kendzi.math.geometry.skeleton.events.chains;

import kendzi.math.geometry.skeleton.circular.Edge;
import kendzi.math.geometry.skeleton.circular.Vertex;

public interface ChainEnds {

    Edge getPreviousEdge();

    Edge getNextEdge();

    Vertex getPreviousVertex();

    Vertex getNextVertex();

    Vertex getCurrentVertex();
}