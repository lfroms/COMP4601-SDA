package edu.carleton.comp4601.resources.SearchableDocumentArchive;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.carleton.comp4601.store.DataCoordinator;


@Path("sda/{id: [0-9]*}")
public class DocumentResource {
	private final static String PARAM_NAME = "id";
	
	private final DataCoordinator coordinator = DataCoordinator.getInstance();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String get(@PathParam(PARAM_NAME) String id) {
		// TODO Need to run this through DocumentInterface when accessing coordinator and transforming.
		return coordinator.find(mapId(id)).get().toJSON().toString();
	}
	
	@DELETE
	public void delete(@PathParam(PARAM_NAME) String id) {
		coordinator.delete(mapId(id));
	}
	
	// PRIVATE HELPERS ==================================================================
	
	private static final Integer mapId(String id) {
		return Integer.valueOf(id);
	}

}
