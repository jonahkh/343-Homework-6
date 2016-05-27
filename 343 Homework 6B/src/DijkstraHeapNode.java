/**
 * This class represents a DijkstraHeapNode to use with the min heap.
 * 
 * @author Jonah Howard
 * @author Jacob Tillett
 */
public class DijkstraHeapNode implements Comparable<DijkstraHeapNode> {
	/** The path length of this node from the root. */
	private int pathLength;
	
	/** The vertex this node points to. */
	private Vertex vertex;
	
	/** Determines whether this vertex is included in the known set. */
	private boolean known = false;
	
	
	/**
	 * Initialize a new DijkstraHeapNode.
	 * 
	 * @param v the vertex this node points to
	 * @param p the path length of this node from the root
	 */
	public DijkstraHeapNode(Vertex v, int p) {
		pathLength = p;
		vertex = v;
	}
	
	/**
	 * Return the pathLength of this node.
	 * 
	 * @return the pathLength of this node
	 */
	public int getDepth() {
		return pathLength;
	}
	
	/**
	 * Return the vertex associated with this node.
	 * 
	 * @return the vertex for this node
	 */
	public Vertex getVertex() {
		return vertex;
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
		return pathLength - other.pathLength;
	}
	
	@Override
	public String toString() {
		return "name: " + vertex.getName() + ", path length: "+ pathLength;
	}

}
