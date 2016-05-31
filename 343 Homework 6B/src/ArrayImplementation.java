import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * This class implements Kijkstra's algorithm using an array of linked lists
 * 
 * @author Jonah Howard
 * @author Jacob Tillett
 *
 */

public class ArrayImplementation implements Algorithm {
	/**	The graph being used  */
	private SimpleGraph myG;
	/** The list of vertices in the graph */
	private LinkedList myVertices;
	/** The list of edges in the graph */ 
	private LinkedList myEdges;
	/** The starting vertex */
	private Vertex start;
	/** The ending vertex */
	private Vertex end;
	/** The array used for finding the shortest path */
	private LinkedList[] myLabels;
	/** Maps each vertex to its corresponding Dijkstras node */
	private Map<Vertex, DijkstraHeapNode> myNodes;
	
		
	/**
	 * Initialize the ArrayImplementation
	 * @param G The graph coming in.
	 */
	public ArrayImplementation(SimpleGraph G) {
		myG = G;
		myVertices = G.vertexList;
		myEdges = G.edgeList;
		double maxEdge = 0;
		double temp = 0;
		Iterator iter = G.edges();
		while(iter.hasNext()) {
			
			temp = (double) ((Edge)iter.next()).getData();
			if(temp > maxEdge) {
				maxEdge = temp;
			}
		}
		myLabels =  new LinkedList[(int) (maxEdge * G.vertexList.size())];
	}

	/**
	 * Runs the algorithm with the passed vertex as the starting point city.
	 * 
	 * @param s the starting city vertex
	 */
	public void runAlgorithm(Vertex s) {
		
		//create the lists at each label
		
		myNodes = new HashMap<Vertex, DijkstraHeapNode>();
		for(int i = 0; i < myLabels.length; i++) {
			myLabels[i] = new LinkedList();
		}
		start = s;
		Iterator iter = myG.vertices();
		//fill array
		while(iter.hasNext()) {
			Vertex v = (Vertex) iter.next();
			DijkstraHeapNode node = new DijkstraHeapNode(v, myLabels.length - 1);
			if(v == start) {
				
				myLabels[0].add(node);
				node.setDistance(0);
				node.setPrev(null);
			} else {
				myLabels[myLabels.length - 1].add(node);
				
			}
			myNodes.put(v, node);
			node.setKnown(false);
			v.setData(node);
			
		}
		
		for(int i = 0; i < myLabels.length; i++) {
			if(!myLabels[i].isEmpty()) {
				Iterator itr = myLabels[i].iterator();
				while(itr.hasNext()) {
					DijkstraHeapNode u = (DijkstraHeapNode) itr.next();
					u.setKnown(true);
					Iterator neighbors = myG.incidentEdges(u.getVertex());
				
					while(neighbors.hasNext()) {
						Edge e = ((Edge) neighbors.next());
						DijkstraHeapNode node = (DijkstraHeapNode) myG.opposite(u.getVertex(), e).getData();
						
						if(!node.isKnown()) {
							int distance = u.getDistance() +((int) (double) e.getData());
							int vDistance = node.getDistance();
							if (distance < vDistance) {
								node.setDistance(distance);
								node.setPrev(u);
								myLabels[vDistance].remove(node);
								myLabels[distance].add(node);
							}
						}
					}
				}
			}
		}
		
	}

	/**
	 * Return the shortest path description starting from the starting vertex and ending at
	 * the last vertex.
	 * 
	 * @param first
	 * @param last
	 * @return the path
	 */
	public String getPath(Vertex first, Vertex last) {
		
		StringBuilder b = new StringBuilder();
		DijkstraHeapNode current = myNodes.get(last);
		DijkstraHeapNode previous = myNodes.get(last).getPrev();
		b.append("End => " + current.toString() + "<br>");
		// Check that the path contains at least two nodes
		if (current.getPrev() != null) {
			current = current.getPrev();
			previous = previous.getPrev();
		}
		while (current.getPrev() != null) {
			b.insert(0, " => " + current.toString() + "<br>");
			current = previous;
			previous = current.getPrev();
		}
		b.insert(0, "Start => " + current.toString() + "<br>");
		b.insert(0, "<html>");
		b.append("</html>");
		return b.toString();
		
	
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
