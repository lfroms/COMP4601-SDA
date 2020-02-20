package edu.carleton.comp4601.resources.SearchableDocumentArchive;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("sda/query/{terms}")
public class QueryResource {
	private final static String PARAM_NAME = "terms";
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getHTML(@PathParam(PARAM_NAME) String terms) {
		return SearchInterface.searchLocalHTML(terms);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Response getXML(@PathParam(PARAM_NAME) String terms) {
		return Response.ok(SearchInterface.searchLocalXML(terms), MediaType.APPLICATION_XML).build();
	}
	
	// For client application.
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getJSON(@PathParam(PARAM_NAME) String terms) {
		return SearchInterface.searchLocalJSON(terms);
	}
	
}
