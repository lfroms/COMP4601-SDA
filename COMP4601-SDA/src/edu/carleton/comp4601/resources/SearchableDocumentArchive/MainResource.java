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
}
