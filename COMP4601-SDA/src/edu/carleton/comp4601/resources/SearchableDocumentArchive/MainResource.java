package edu.carleton.comp4601.resources.SearchableDocumentArchive;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.carleton.comp4601.helpers.HTMLFrameGenerator;
import edu.carleton.comp4601.utility.SearchServiceManager;

@Path("sda")
public class MainResource {
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String displayLanding() {
		String contentHtml = "<h1>COMP4601 Searchable Document Archive: Lukas Romsicki & Britta Evans-Fenton</h1>";
		return HTMLFrameGenerator.wrapInHTMLFrame("SDA", contentHtml);
	}
	
	// SERVICES =========================================================================
	
	@GET
	@Path("reset")
	@Produces(MediaType.TEXT_HTML)
	public Response reset() {
		try {
			MainInterface.resetAll();

			return Response.ok(
					HTMLFrameGenerator.wrapInHTMLFrame("Reset", "<strong>Successfully reset all documents.</strong>")
				).build();
			
		} catch (Exception e) {
			return Response.notModified(
					HTMLFrameGenerator.wrapInHTMLFrame("Reset Failed", "<strong>Failed to reset documents.</strong>")
				).build();
		}	
	}
	
	@GET
	@Path("list")
	@Produces(MediaType.TEXT_HTML)
	public String list() {
		String listHtml = String.join("<br />", SearchServiceManager.getInstance().list());
		return HTMLFrameGenerator.wrapInHTMLFrame("Available Services", listHtml);
	}
	
	@GET
	@Path("pagerank")
	@Produces(MediaType.TEXT_HTML)
	public String pageRank() {
		return MainInterface.getPageRankList();
	}
	
	@GET
	@Path("boost")
	public Response boost() {
		MainInterface.reIndexWithBoost();
		
		return Response.ok().build();
	}
	
	@GET
	@Path("noboost")
	public Response noBoost() {
		MainInterface.reIndexRemovingBoost();
		
		return Response.ok().build();
	}
}
