package edu.carleton.comp4601.store.graph;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.Multigraph;

import edu.carleton.comp4601.models.Identifiable;
import edu.carleton.comp4601.models.Locatable;
import edu.carleton.comp4601.store.Storable;

public final class GraphProvider<DocumentType extends Identifiable & Locatable> implements Storable<DocumentType> {
	
	private final Graph<DocumentType, DefaultEdge> graph = new Multigraph<>(DefaultEdge.class);

	@Override
	public void upsert(DocumentType input) {
		if (graph.containsVertex(input)) {
			graph.removeVertex(input);
		}
		
		graph.addVertex(input);
		addEdgeIfParentExists(input);
	}

	@Override
	public Optional<DocumentType> find(Integer id) {
		return graph.vertexSet().stream().filter(x -> x.getId() == id).findFirst();
	}
	
	public Map<Integer, Double> getRanksForAllObjects() {
		return PageRanker.computeRanks(graph);
	}
	
	// PRIVATE HELPERS ==================================================================
	
	private final Boolean addEdgeIfParentExists(DocumentType vertex) {
		Optional<DocumentType> parentVertexOptional = find(vertex.getURL().getParentDocid());

		try {
			DocumentType parentVertex = parentVertexOptional.get();
			graph.addEdge(parentVertex, vertex);
			
			return true;

		} catch(NoSuchElementException e) {
			return false;
		}
	}
}
