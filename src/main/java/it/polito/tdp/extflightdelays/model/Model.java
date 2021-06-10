package it.polito.tdp.extflightdelays.model;

import java.util.HashMap;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;


public class Model {
	
	private Graph<String, DefaultWeightedEdge> grafo ;
	private ExtFlightDelaysDAO dao;
	private Map<Integer, Airport> aMap;
	
	public Model() {
		dao = new ExtFlightDelaysDAO();
		aMap = new HashMap<>();
	}

	public void creaGrafo(int compagnie) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
	}
}
