import java.util.ArrayList;
import java.util.List;



public class Graph {

	private List<Node> nodes; // Noeuds
	private List<Edge> edges; // Les arcs
	
	public Graph() {
		// A compléter 
	}
	
	public List<Edge> getEdgesGoingFrom(Node source) {
		// A complèter 
		return null;
		
	}
	public List<Edge> getEdgesGoingTo(Node dest) {
		// A complèter 
		return null;
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
