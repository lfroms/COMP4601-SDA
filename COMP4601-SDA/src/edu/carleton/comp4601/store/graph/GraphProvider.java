package edu.carleton.comp4601.store.graph;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.Multigraph;

import edu.carleton.comp4601.models.Identifiable;
import edu.carleton.comp4601.models.Locatable;
import edu.carleton.comp4601.store.DocumentMapper;
import edu.carleton.comp4601.store.MappableProvider;
import edu.carleton.comp4601.store.Storable;

public final class GraphProvider<DocumentType extends Identifiable & Locatable> extends MappableProvider<DocumentType>
		implements Storable<DocumentType> {
	
	private final Graph<DocumentType, DefaultEdge> graph = new Multigraph<>(DefaultEdge.class);

	public GraphProvider(Supplier<? extends DocumentMapper<DocumentType>> mapperConstructor) {
		super(mapperConstructor);
		// TODO Auto-generated constructor stub
	}

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
