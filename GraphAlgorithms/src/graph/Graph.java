package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Graph<T> {

	List<GraphNode<T>> vertices;
	
	public Graph(){
		vertices = new ArrayList<GraphNode<T>>();
	}
	
	public void addVertex(GraphNode<T> n){
		vertices.add(n);
	}
	
	/**
	 * Gets the list of vertices currently inserted in this graph
	 * @return 
	 */
	public List<GraphNode<T>> getVertices(){
		return vertices;
	}
	
	/**
	 * Runs Dijkstra's algorithm on the graph, starting from the node that is passed in.
	 * Note that Dijkstra's runs on ONLY nodes with POSITIVE weights.
	 * @param graph the graph which Dijkstra's algorithm will run on
	 * @param n the node to start Dijkstra's algorithm
	 * @return a Map which maps each target node to a map containing the node to start from and the total price
	 */
	public static <T> Map<GraphNode<T>, Integer> dijkstra(Graph<T> graph, GraphNode<T> n){
		/* For every vertex, initialize distance to infinity. 
		 * Take the smallest weight path, update new distance
		 * Repeat while there are any unvisted nodes.
		 */

		// Set all nodes distances to infinity, and all nodes are treated as unvisited.
		Map<GraphNode<T>, Integer> knownShortestPaths = new HashMap<GraphNode<T>, Integer>();
		for (GraphNode<T> node: graph.getVertices()){
			knownShortestPaths.put(node,-1);
		}
		// First node is zero.
		knownShortestPaths.put(n, 0);
		Set<GraphNode<T>> visitedNodes = new HashSet<GraphNode<T>>();
		
		// Setup Priority Queue. Start with starting node n.
		class PriorityQueueNode implements Comparable<PriorityQueueNode>{
			private GraphNode<T> node;
			public int weight;
			
			public PriorityQueueNode(GraphNode<T> node, int weight) {
				this.setNode(node);
				this.weight = weight;
			}
			
			@Override
			public int compareTo(PriorityQueueNode o) {
				if (this.weight < o.weight) return -1;
				if (this.weight > o.weight) return 1;
				return 0;
			}

			public GraphNode<T> getNode() {
				return node;
			}

			public void setNode(GraphNode<T> node) {
				this.node = node;
			};
		}
		
		Queue<PriorityQueueNode> minimalEdges = new PriorityQueue<PriorityQueueNode>();
		minimalEdges.add(new PriorityQueueNode(n, 0));
		
		while (!minimalEdges.isEmpty()){
			// Update our distance list with the nodes
			GraphNode<T> currentNode = minimalEdges.remove().getNode();
			// The baseDistance is the current known distance to this node, we add all neighbor distances to this node
			int baseDistance = knownShortestPaths.get(currentNode);
			if (baseDistance == -1) baseDistance = 0;
			// Visit this node. This means processing all of its edges.
			for (GraphNode<T> edge : currentNode.getEdgeList().keySet()){
				// Update shortestPath map with new distances
				int edgeWeight = baseDistance + currentNode.getEdgeWeight(edge);
				// Current best distance to this node
				int currentBest = knownShortestPaths.get(edge);
				
				// If the edge weight is an improvement over current values, or if it is infinity (-1), update our known shortest paths
				if (edgeWeight < currentBest || currentBest == -1){
					knownShortestPaths.put(edge, edgeWeight); 
				}
				//if we have not already visited this node, add it.
				if (!visitedNodes.contains(edge)){ 
					minimalEdges.add(new PriorityQueueNode(edge, edgeWeight));
				}
			}
			// currentNode is now visited. Add it to visitedNodes so we do not check it again
			visitedNodes.add(currentNode);
		}
		
		// When the above while loop finishes, we have processed everything.
		
		return knownShortestPaths;
	}
	

	public static void main(String[] args) {
		Graph<Integer> graph = new Graph<Integer>();
		GraphNode<Integer> a = new GraphNode<Integer>(1);
		GraphNode<Integer> b = new GraphNode<Integer>(2);
		GraphNode<Integer> c = new GraphNode<Integer>(3);
		GraphNode<Integer> d = new GraphNode<Integer>(4);
		GraphNode<Integer> e = new GraphNode<Integer>(5);
		GraphNode<Integer> f = new GraphNode<Integer>(6);
		a.addEdge(b, 7);
		b.addEdge(a, 7);
		
		a.addEdge(f,14);
		f.addEdge(a,14);
		
		a.addEdge(c,9);
		c.addEdge(a,9);
		
		c.addEdge(f,2);
		f.addEdge(c,2);
		
		c.addEdge(d,11);
		d.addEdge(c,11);
		
		b.addEdge(c,10);
		c.addEdge(b,10);
		
		b.addEdge(d,15);
		d.addEdge(b,15);
		
		e.addEdge(f,9);
		f.addEdge(e,9);
		
		e.addEdge(d,6);
		d.addEdge(e,6);
		graph.addVertex(a);
		graph.addVertex(b);
		graph.addVertex(c);
		graph.addVertex(d);
		graph.addVertex(e);
		graph.addVertex(f);
		
		Map<GraphNode<Integer>, Integer> result = dijkstra(graph, a);
		for (GraphNode<Integer> node : result.keySet()){
			System.out.print("Distance to: " + node.getData());
			System.out.println(" is " + result.get(node));
		}
		
	}
}
