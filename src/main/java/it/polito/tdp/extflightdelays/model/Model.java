package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;


public class Model {
	
	private Graph<Airport, DefaultWeightedEdge> grafo ;
	private ExtFlightDelaysDAO dao;
	private Map<Integer, Airport> aMap;
	private List<Airport> vertici;
	private List<Airport> percorso;
	
	public Model() {
		dao = new ExtFlightDelaysDAO();
		aMap = new HashMap<>();
		dao.loadAllAirports(aMap);
	}

	public void creaGrafo(int compagnie) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		vertici = new ArrayList<>();
		Graphs.addAllVertices(grafo, dao.getVertici(compagnie, aMap));
		vertici.addAll(dao.getVertici(compagnie, aMap));
		
		for(Adiacenza a: dao.getArchi()) {
			if(grafo.vertexSet().contains(aMap.get(a.getA1())) && grafo.vertexSet().contains(aMap.get(a.getA2()))) {
				Graphs.addEdge(grafo, aMap.get(a.getA1()), aMap.get(a.getA2()), a.getPeso());
			}
		}
	}
	
	public int vertexNumber() {
		return grafo.vertexSet().size();
	}
	
	public int edgeNumber() {
		return grafo.edgeSet().size();
	}
	
	public List<Airport> getVertici(){
		return vertici;
	}
	
	public List<Airport> percorso(Airport partenza, Airport arrivo){
		percorso = null;
		
		List<Airport> parziale = new ArrayList<>();
		parziale.add(partenza);
		
		cerca(parziale, arrivo);
		
		return percorso;
	}
	
	public void cerca(List<Airport> parziale, Airport arrivo) {
		Airport ultimo = parziale.get(parziale.size()-1);
		if(arrivo.equals(ultimo)) {
			percorso = new ArrayList<>(parziale);
		}
		for(Airport a: Graphs.neighborListOf(grafo, ultimo)) {
			if(!parziale.contains(a)) {
				parziale.add(a);
				cerca(parziale, arrivo);
				parziale.remove(parziale.size()-1) ;
			}
		}
	}
}
