package edu.carleton.comp4601.resources.SearchableDocumentArchive;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("sda/documents")
public class DocumentsResource {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getJSON() {
		return DocumentsInterface.getMultiJSON();
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getHTML() {
		return DocumentsInterface.getMultiHTML();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String getXML() {
		// TODO Return XML
		return "";
	}
	
}
