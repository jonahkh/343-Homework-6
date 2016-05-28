import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


/**
 * This class implements Dijkstra's algorithm using a min heap.
 * 
 * @author Jonah Howard
 * @author Jacob Tillett
 */
public class MinHeapImplementation implements Algorithm{
	/** The priority heap for the heap nodes. */
	private BinaryHeap heap;
	
	/** The current graph being evaluated. */
	private SimpleGraph g;
	
	/** A list of all vertices for current graph. */
	private LinkedList vertices;
	
	/** A list of all the edges for the current graph. */
	private LinkedList edges;
	
	/** The starting vertex. */
	private Vertex start;
	
	/** The ending vertex. */
	private Vertex end;
	
	/** Maps each vertex to its corresponding DijkstraHeapNode. */
	private Map<Vertex, DijkstraHeapNode> myNodes;
	

	/**
	 * Initialize a new MinHeapImplementation.
	 */
	public MinHeapImplementation(SimpleGraph G) {
		heap = new BinaryHeap();
		g = G;
		vertices = G.vertexList;
		edges = G.edgeList;
	}
	
	/**
	 * Runs the algorithm with the passed vertex as the starting point city.
	 * 
	 * @param s the starting city vertex
	 */
	public void runAlgorithm(Vertex s) {
		start = s;
		myNodes = new HashMap<Vertex, DijkstraHeapNode>();
		Iterator iter = g.vertices();
		// Populate the heap
		while (iter.hasNext()) {
			Vertex v = (Vertex) iter.next();
			int path = Integer.MAX_VALUE;
			DijkstraHeapNode node = new DijkstraHeapNode(v, path);
			if (v == start)  {
				node.setDistance(0);
				node.setPrev(null);
			}
			myNodes.put(v, node);
//			nodes.add(node);
			node.setKnown(false);
			v.setData(node);
			heap.insert(node);
		}
		
		while (!heap.isEmpty()) {
			DijkstraHeapNode u;
			try {
				u = (DijkstraHeapNode) heap.deleteMin();
				u.setKnown(true);
//				System.out.println(u.toString());
				Iterator neighbors = g.incidentEdges(u.getVertex());
				while (neighbors.hasNext()) {
					Edge e = ((Edge) neighbors.next());
					DijkstraHeapNode node = (DijkstraHeapNode) g.opposite(u.getVertex(), e).getData();
//					System.out.println("current neighbor = " + node.toString());
					if (!node.isKnown()) {
						int distance = u.getDistance() + ((int) (double) e.getData());
						int vDistance = node.getDistance();
						if (distance < vDistance) {
//							System.out.println("new distance = " + distance);
							node.setDistance(distance);
							node.setPrev(u);
							heap.percolateUp(node);
						}
					}
				}
				
			} catch (EmptyHeapException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Return the shortest path description starting from the starting vertex and ending at
	 * the last vertex.
	 * 
	 * @param first
	 * @param last
	 * @return
	 */
	public String getPath(Vertex first, Vertex last) {
		StringBuilder builder = new StringBuilder();
		DijkstraHeapNode current = myNodes.get(last);
		DijkstraHeapNode previous = myNodes.get(last).getPrev();
		builder.append("End => " + current.toString() + "<br>");
		// Check that the path contains at least two nodes
		if (current.getPrev() != null) {
			current = current.getPrev();
			previous = previous.getPrev();
		}
		while (current.getPrev() != null) {
			builder.insert(0, " => " + current.toString() + "<br>");
			current = previous;
			previous = current.getPrev();
		}
		builder.insert(0, "Start => " + current.toString() + "<br>");
		builder.insert(0, "<html>");
		builder.append("</html>");
		return builder.toString();
	}
	
	/**
	 * Reruns the algorithm with the passed node as the starting city.
	 * 
	 * @param node the new starting node
	 */
	public void reRunAlgorithm(DijkstraHeapNode node) {
		runAlgorithm(node.getVertex());
	}
	
	/**
	 * Return a set of all the vertices.
	 * 
	 * @return a set of all the vertices
	 */
	public Set<Vertex> getVertices() {
		return myNodes.keySet();
	}
	
	/**
	 * Returns the DijkstraHeapNode corresponding to the passed vertex.
	 * 
	 * @param v the vertex being searched for
	 * @return the DijkstraHeapNode corresponding to v
	 */
	public DijkstraHeapNode getCorrespondingNode(Vertex v) {
		return myNodes.get(v);
	}
}
