import java.util.ArrayList;
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
	
	private List<DijkstraHeapNode> nodes;

	/**
	 * Initialize a new MinHeapImplementation.
	 */
	public MinHeapImplementation(SimpleGraph G) {
		heap = new BinaryHeap();
		g = G;
		vertices = G.vertexList;
		edges = G.edgeList;
		nodes = new ArrayList<>();
	}
	
	/**
	 * Runs the algorithm with the passed vertex as the starting point city.
	 * 
	 * @param s the starting city vertex
	 */
	public void runAlgorithm(Vertex s) {
		start = s;
		Iterator iter = g.vertices();
		// Populate the heap
		while (iter.hasNext()) {
			Vertex v = (Vertex) iter.next();
			int path = Integer.MAX_VALUE;
			if (v == start)  {
				path = 0;
			}
			DijkstraHeapNode node = new DijkstraHeapNode(v, path);
			nodes.add(node);
			node.setKnown(false);
			v.setData(node);
			heap.insert(node);
		}
		
		while (!heap.isEmpty()) {
			System.out.println();
			DijkstraHeapNode u;
			try {
				u = (DijkstraHeapNode) heap.deleteMin();
				u.setKnown(true);
				System.out.println(u.toString());
				Iterator neighbors = g.incidentEdges(u.getVertex());
				while (neighbors.hasNext()) {
					Edge e = ((Edge) neighbors.next());
					DijkstraHeapNode node = (DijkstraHeapNode) g.opposite(u.getVertex(), e).getData();
					System.out.println("current neighbor = " + node.toString());
					if (!node.isKnown()) {
						int distance = u.getDistance() + ((int) (double) e.getData());
						int vDistance = node.getDistance();
						if (distance < vDistance) {
							System.out.println("new distance = " + distance);
							node.setDistance(distance);
							node.setVertex(u.getVertex());
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
	 * Reruns the algorithm with the passed node as the starting city.
	 * 
	 * @param node the new starting node
	 */
	public void reRunAlgorithm(DijkstraHeapNode node) {
		runAlgorithm(node.getVertex());
	}
	
	/**
	 * Return the list of all DijkstraHeapNodes.
	 * 
	 * @return the list of all DijkstraHeapNodes
	 */
	public List<DijkstraHeapNode> getNodes() {
		return nodes;
	}
}
