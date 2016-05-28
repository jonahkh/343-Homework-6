/**
 * This class represents a DijkstraHeapNode to use with the min heap.
 * 
 * @author Jonah Howard
 * @author Jacob Tillett
 */
public class DijkstraHeapNode implements Comparable<DijkstraHeapNode> {
	
	/** The distance of this node from the root. */
	private int distance;
	
	/** The vertex this node points to. */
	private Vertex vertex;
	
	/** Determines whether this vertex is included in the known set. */
	private boolean known = false;
	
	/** The vertex that points to this node's vertex. */
	private Vertex prev;
	
	
	/**
	 * Initialize a new DijkstraHeapNode.
	 * 
	 * @param v the vertex this node points to
	 * @param p the path length of this node from the root
	 */
	public DijkstraHeapNode(Vertex v, int p) {
		distance = p;
		vertex = v;
	}
	
	/**
	 * Return the distance of this node.
	 * 
	 * @return the distance of this node
	 */
	public int getDistance() {
		return distance;
	}
	
	/**
	 * Set the distance of this node from the root to the passed value.
	 * 
	 * @param d the new distance for this node
	 */
	public void setDistance(int d) {
		distance = d;
	}
	
	/**
	 * Set the path length of 
	 */
	
	/**
	 * Return the vertex associated with this node.
	 * 
	 * @return the vertex for this node
	 */
	public Vertex getVertex() {
		return vertex;
	}
	
	/**
	 * Set the vertex pointing to this node's vertex to the passed vertex.
	 * 
	 * @param prev the vertex pointing to this node's vertex
	 */
	public void setVertex(Vertex prev) {
		this.prev = prev;
	}
	
	/**
	 * Return if this vertex is in the known set.
	 * 
	 * @return true if this vertex is in the known set, false otherwise
	 */
	public boolean isKnown() {
		return known;
	}
	
	/**
	 * Sets whether the vertex represented by this DijkstraHeapNode is in the known set.
	 * 
	 * @param bool true if the vertex is in the known set, false otherwise
	 */
	public void setKnown(boolean bool) {
		known = bool;
	}
	
	@Override
	public int compareTo(DijkstraHeapNode other) {
		return distance - other.distance;
	}
	
	@Override
	public String toString() {
		return vertex.getName().toString();
	}

}
