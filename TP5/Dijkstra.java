import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;



public class Dijkstra {

	private Graph graph;
	private Map<Node, Edge> dijkstraTable[];
	private Stack<Edge> path;
	private int iteration = 0;
	

	public Dijkstra (Graph g) {
		this.graph = g;
	}

	public void findPath(Node s, Node d) {

		dijkstraTable = new HashMap[graph.getNodes().size() + 1];
		path = new Stack<Edge>();

		iteration = 0;
		Node currentNode;
		List<Edge> nodeToTestList;
		int currentDistance;
		Edge nextEdgeToTest;


		// A compléter

		//Initialisation du tableau
		dijkstraTable[iteration] = new HashMap<Node, Edge>();
		for (int i = 0 ; i < graph.getNodes().size();i++){
			dijkstraTable[iteration].put(graph.getNodes().get(i), null );
		}
		dijkstraTable[iteration].replace(s,new Edge(s,s,0));

		//Pour remplir tableau Dijkstra
		do{
			currentNode = getMinimum(dijkstraTable[iteration]);
			nodeToTestList = graph.getEdgesGoingFrom(currentNode);
			currentDistance = dijkstraTable[iteration].get(currentNode).getDistance();
			iteration++;
			dijkstraTable[iteration] = new HashMap<Node, Edge>();
			for (int i = 0 ; i < graph.getNodes().size();i++){
				if(dijkstraTable[iteration-1].get(graph.getNodes().get(i)) != null)
					dijkstraTable[iteration].put(graph.getNodes().get(i), new Edge(dijkstraTable[iteration-1].get(graph.getNodes().get(i)).getSource(),dijkstraTable[iteration-1].get(graph.getNodes().get(i)).getDestination(),dijkstraTable[iteration-1].get(graph.getNodes().get(i)).getDistance(),dijkstraTable[iteration-1].get(graph.getNodes().get(i)).getTested()));
				else
					dijkstraTable[iteration].put(graph.getNodes().get(i),null);
			}

			for ( int i = 0 ; i < nodeToTestList.size() ; i++){
				nextEdgeToTest = nodeToTestList.get(i);
				dijkstraTable[iteration].put(nextEdgeToTest.getDestination(),getMinimum(dijkstraTable[iteration].get(nextEdgeToTest.getDestination()),new Edge(currentNode,nextEdgeToTest.getDestination(),currentDistance + nextEdgeToTest.getDistance(), false  )));
			}
			dijkstraTable[iteration].get(currentNode).setTested(true);
		}while(getMinimum(dijkstraTable[iteration]) != d);

		//Pour cree le path
		Edge firstEdge = dijkstraTable[iteration].get(d);
		while (firstEdge.getSource() != firstEdge.getDestination() ){
			path.add(firstEdge);
			firstEdge = dijkstraTable[iteration].get(firstEdge.getSource());
		}
	}

	private Node getMinimum(Map<Node, Edge> map) {
		Edge min = null;
		for (Node Key : map.keySet()) {
			if(map.get(Key) != null){
				if ( (min == null || map.get(Key).getDistance() < min.getDistance()) && map.get(Key).getTested() == false) {
					min = map.get(Key);
				}
			}
		}
		if (min == null){
			return null;
		}else{
			return min.getDestination();
		}
	}

	private Edge getMinimum (Edge e1, Edge e2) {
		// A completer
		if(e1 != null){
			if(e1.getDistance()< e2.getDistance()){
				return e1;
			}else{
				return e2;
			}
		}else{
			return e2;
		}
	}
	
	public String printShortPath(Node source, Node destination) {
		// A completer
		findPath(source,destination);

		String output = "Shortest path: \n" + source.getName();
		while (path.empty() == false){
			output += " -> " + path.pop().getDestination().getName();
		}
		return output;
	}

	public void showTable() {
		// A completer
		String output = "|  A |  B |  C |  D |  E |  F |  G |\n";
		for ( int i = 0 ; i <= iteration; i++){
			for (int j = 0 ; j < graph.getNodes().size() ; j++){
				if (dijkstraTable[i].get(graph.getNodes().get(j)) != null) {
					if(dijkstraTable[i].get(graph.getNodes().get(j)).getTested() == false)
						output += "| " + dijkstraTable[i].get(graph.getNodes().get(j)).getSource().getName() + dijkstraTable[i].get(graph.getNodes().get(j)).getDistance() + " ";
					else
						output += "|    ";
				}else{
					output += "| .  ";
				}
			}
			output += "|\n";
		}
		System.out.println(output);
		
	}
}
