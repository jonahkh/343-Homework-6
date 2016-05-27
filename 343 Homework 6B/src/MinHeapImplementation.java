import java.util.Iterator;
import java.util.LinkedList;


/**
 * This class implements Dijkstra's algorithm using a min heap.
 * 
 * @author Jonah Howard
 * @author Jacob Tillett
 */
public class MinHeapImplementation {
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

	/**
	 * Initialize a new MinHeapImplementation.
	 */
	public MinHeapImplementation(SimpleGraph G, Vertex s) {
		heap = new BinaryHeap();
		g = G;
		vertices = G.vertexList;
		edges = G.edgeList;
		start = s;
	}
	
	public int runAlgorithm() {
		Iterator iter = g.vertices();
		// Populate the heap
		while (iter.hasNext()) {
			int path = -1;
			Vertex v = (Vertex) iter.next();
			if (v != start)  {
				heap.insert(new DijkstraHeapNode(v, path));
			}
		}
//		examine();
		while (!heap.isEmpty()) {
			DijkstraHeapNode v;
			try {
				v = (DijkstraHeapNode) heap.deleteMin();
				Iterator neighbors = g.incidentEdges(v.getVertex());
				
			} catch (EmptyHeapException e) {
				e.printStackTrace();
			}
		}

		return -1;
	}
	
	/**
	 * Examine and print the contents of the current heap.
	 */
	private void examine() {
		while (!heap.isEmpty()) {
			try {
				System.out.println(heap.deleteMin().toString());
			} catch (EmptyHeapException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
