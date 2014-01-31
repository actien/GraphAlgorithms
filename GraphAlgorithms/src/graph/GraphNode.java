package graph;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GraphNode<T> {
	private Map<GraphNode<T>, Integer> edges;
	private T data;
	
	public GraphNode(){
		edges = new HashMap<GraphNode<T>, Integer>();
	}
	
	public GraphNode(T data){
		this();
		this.data = data;
	}
	
	public T getData(){
		return data;
	}
	
	public Map<GraphNode<T>,Integer> getEdgeList(){
		return edges;
	}
	
	public Set<GraphNode<T>> getNeighbors(){
		return edges.keySet();
	}
	
	public void addEdge(GraphNode<T> n, Integer weight){
		edges.put(n, weight);
	}
	
	public void addEdge(GraphNode<T> n){
		addEdge(n, 0);
	}
	
	/**
	 * Check whether this node has an edge to the other node
	 * @param n the other node
	 * @return true if an edges exists, false otherwise
	 */
	public boolean hasEdge(GraphNode<T> n){
		return edges.containsKey(n); 
	}
	
	public int getEdgeWeight(GraphNode<T> n){
		if (hasEdge(n)){
			return edges.get(n);
		}
		return -1;
	}
	
	public GraphNode<T> getNode(GraphNode<T> n){
		if (hasEdge(n)){
			return n;
		}
		return null;
	}
		
	
	public static void main(String[] args) {
		System.out.println("Created a->b->c->a");
		GraphNode<Integer> a = new GraphNode<Integer>();
		GraphNode<Integer> b = new GraphNode<Integer>();
		GraphNode<Integer> c = new GraphNode<Integer>();
		a.addEdge(b);
		b.addEdge(c);
		c.addEdge(a);
		System.out.println(a.getData());
	}
}
