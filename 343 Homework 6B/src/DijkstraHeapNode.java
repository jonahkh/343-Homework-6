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
	private DijkstraHeapNode prev;
	
	/** The index of this node's location in the heap. */
	 private int index;
	
	
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
	 * Return the node pointing to this node.
	 * 
	 * @return the node pointing to this node
	 */
	public DijkstraHeapNode getPrev() {
		return prev;
	}
	
	/**
	 * Set the node pointing to this node to the passed value.
	 * 
	 * @param node the node that points to this node
	 */
	public void setPrev(DijkstraHeapNode node) {
		prev = node;
	}
	
	/**
	 * Set the index of this node's location in the heap to the passed value.
	 * 
	 * @param i the index
	 */
	public void setIndex(int i) {
		index = i;
	}
	
	/**
	 * Return the current index of this node in the heap.
	 * 
	 * @return the index of this node
	 */
	public int getIndex() {
		return index;
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
