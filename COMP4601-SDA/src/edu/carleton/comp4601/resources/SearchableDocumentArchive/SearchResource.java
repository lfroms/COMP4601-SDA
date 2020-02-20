package edu.carleton.comp4601.resources.SearchableDocumentArchive;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("sda/search/{terms}")
public class SearchResource {
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getHTML() {
		// TODO Return HTML
		return "";
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String getXML() {
		// TODO Return XML
		return "";
	}
	
	// For client application.
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getJSON() {
		// TODO Return JSON
		return "";
	}
	
}
