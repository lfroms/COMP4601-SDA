package edu.carleton.comp4601.resources.SearchableDocumentArchive;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("sda/{id: [0-9]*}")
public class DocumentResource {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String get(@PathParam("id") String id) {
		return "";
	}

}
