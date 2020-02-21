package edu.carleton.comp4601.resources.SearchableDocumentArchive;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
		return DocumentsInterface.getMultiXML();
	}
	
	@DELETE
	@Path("delete/{query}")
	public Response deleteByQuery(@PathParam("query") String query) {
		if (DocumentsInterface.deleteByQuery(query)) {
			return Response.ok().build();
		}
		
		return Response.noContent().build();
	}
}
