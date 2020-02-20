package edu.carleton.comp4601.resources.SearchableDocumentArchive;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/sda")
public class DocumentController {
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String displayLanding() {
		return "<h1>COMP4601 Searchable Document Archive: Lukas Romsicki & Britta Evans-Fenton</h1>";
	}
}
