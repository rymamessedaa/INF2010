import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



public class Graph {

	private List<Node> nodes; // Noeuds
	private List<Edge> edges; // Les arcs
	
	public Graph() {
		// A compléter
		nodes = new LinkedList<Node>();
		edges = new LinkedList<Edge>();
	}
	
	public List<Edge> getEdgesGoingFrom(Node source) {
		// A complèter
		List<Edge> returnList = new LinkedList<Edge>();
		for (int i = 0 ; i < edges.size(); i++){
			if (edges.get(i).getSource() == source)
				returnList.add(edges.get(i));
		}
		return returnList;
		
	}
	public List<Edge> getEdgesGoingTo(Node dest) {
		// A complèter 
		List<Edge> returnList = new LinkedList<Edge>();
		for (int i = 0 ; i < edges.size(); i++){
			if (edges.get(i).getDestination() == dest)
				returnList.add(edges.get(i));
		}
		return returnList;
	}
	
	// Accesseurs 
	public List<Node> getNodes() {
		return nodes;
	}
	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
	public List<Edge> getEdges() {
		return edges;
	}
	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}
	
}
