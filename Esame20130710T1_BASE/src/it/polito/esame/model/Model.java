package it.polito.esame.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import it.polito.esame.bean.Parola;
import it.polito.esame.dao.ParoleDAO;

public class Model {
	
	private Graph<Parola, DefaultEdge> graph ;
	private ParoleDAO dao ;
	
	public Model() {
		
	}
	
	public void createGraph(int size) {

		if(size <= 0)
			throw new RuntimeException();
		
		graph = new SimpleGraph<>(DefaultEdge.class); // ho creato il grafo 
		dao = new ParoleDAO();
		
		List<Parola> words = dao.getAllParolaWithLength(size);
		
		Graphs.addAllVertices(this.graph, words); // ho aggiunto i vertici
		
		for(Parola p : graph.vertexSet()) {
			List<Parola> connessi = getAllConnected(p, words);
			System.out.println("Parola = " + p.getNome());
			System.out.println(connessi);
			for(Parola c:connessi) {
				graph.addEdge(p, c);
			}
			
		}
	}

	private List<Parola> getAllConnected(Parola p, List<Parola> words) {
		
		List<Parola> result = new ArrayList<>();
		for(Parola w : words) {
			if(!p.equals(w)) {
				if(areClose(p,w)) {
					result.add(w);
				}
			}
		}
		return result;
	}

	private boolean areClose(Parola p, Parola w) {
		int count = 0;
		for(int i = 0; i < p.getNome().length(); i++) {
			if(p.getNome().charAt(i) != w.getNome().charAt(i))
				count++;
		}
		if(count == 1)
			return true ;
		
		return false;
	}

	public int getNumberOfWords() {
		
		return this.graph.vertexSet().size();
	}

	public int getNumberOfCC() {
		
		Set<Parola> set = new HashSet<>();
		
		DepthFirstIterator<Parola, DefaultEdge> dfi = new DepthFirstIterator<>(this.graph);
		
		while(dfi.hasNext()) {
			set.add(dfi.next());
		}
		
		return set.size();
	}

	public String getMaxWord() {
		
		String best = null ;
		int bestint = 0;
		for(Parola p : graph.vertexSet()) {
			if(graph.degreeOf(p) > bestint) {
				bestint = graph.degreeOf(p);
				best = p.getNome();
			}
		}
		
		return best;
	}

	public List<Parola> getPath(String iniziale, String finale) throws Exception {
		
		Parola p1 = dao.getParola(iniziale);
		Parola p2 = dao.getParola(finale);
		
		if(p1!=null && p2!=null) {
			if(this.graph==null)
				this.createGraph(p1.getNome().length());
			if(graph.containsVertex(p1) && graph.containsVertex(p2)) {
				ShortestPathAlgorithm<Parola, DefaultEdge> spa = new DijkstraShortestPath<>(this.graph);
				GraphPath<Parola, DefaultEdge> gp = spa.getPath(p1, p2);
				
				List<Parola> result = gp.getVertexList();
				return result ;
			}
			else
				throw new Exception();
		
		}
			
		return null;
	}
}
