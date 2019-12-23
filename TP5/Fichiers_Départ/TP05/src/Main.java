


public class Main {
	
	public static void main(String[] args) {
		Graph g = new Graph();
		System.out.println("TP05 : Graphes");
		
		// Partie 1: A completer : Création du graphe
		
		
		// Partie 2: A completer : Implémentation de l’algorithme Dijkstra
		
		Dijkstra d = new Dijkstra(g);
		
		d.findPath(null, null/* Spécifiez les paramètres */);
		
		d.showTable();

		// Partie 3 : Afficher le chemin le plus court
		System.out.println(d.printShortPath(null, null/* Spécifiez les paramètres */));
	
	}
}
