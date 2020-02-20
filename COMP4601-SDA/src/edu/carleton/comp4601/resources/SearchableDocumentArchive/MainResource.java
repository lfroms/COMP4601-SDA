package edu.carleton.comp4601.resources.SearchableDocumentArchive;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("sda")
public class MainResource {
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String displayLanding() {
		return "<h1>COMP4601 Searchable Document Archive: Lukas Romsicki & Britta Evans-Fenton</h1>";
	}
	
	// SERVICES =========================================================================
	
	@GET
	@Path("reset")
	@Produces(MediaType.TEXT_HTML)
	public String reset() {
		return "";
	}
	
	@GET
	@Path("list")
	@Produces(MediaType.TEXT_HTML)
	public String list() {
		return "";
	}
	
	@GET
	@Path("pagerank")
	@Produces(MediaType.TEXT_HTML)
	public String pageRank() {
		return "";
	}
	
	@GET
	@Path("boost")
	public void boost() {
		
	}
	
	@GET
	@Path("noboost")
	public void noBoost() {
		
	}

}
