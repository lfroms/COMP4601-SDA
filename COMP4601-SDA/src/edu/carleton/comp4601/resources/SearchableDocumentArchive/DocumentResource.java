package edu.carleton.comp4601.resources.SearchableDocumentArchive;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("sda/{id : \\d+}")
public class DocumentResource {
	private final static String PARAM_NAME = "id";
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getHTML(@PathParam(PARAM_NAME) String id) {
		return DocumentInterface.getSingleHTML(mapId(id));
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String getXML(@PathParam(PARAM_NAME) String id) {
		return DocumentInterface.getSingleXML(mapId(id));
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getJSON(@PathParam(PARAM_NAME) String id) {
		return DocumentInterface.getSingleJSON(mapId(id));
	}
	
	@DELETE
	public void delete(@PathParam(PARAM_NAME) String id) {
		DocumentInterface.delete(mapId(id));
	}
	
	// PRIVATE HELPERS ==================================================================
	
	private static final Integer mapId(String id) {
		return Integer.valueOf(id);
	}

}
