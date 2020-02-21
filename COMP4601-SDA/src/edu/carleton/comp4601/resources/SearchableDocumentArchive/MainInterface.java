package edu.carleton.comp4601.resources.SearchableDocumentArchive;

import java.util.ArrayList;

import edu.carleton.comp4601.helpers.HTMLFrameGenerator;
import edu.carleton.comp4601.models.WebDocument;
import edu.carleton.comp4601.store.DataCoordinator;

final class MainInterface {
	private final static DataCoordinator coordinator = DataCoordinator.getInstance();

	public static void resetAll() {
		coordinator.reset();
	}

	public static String getPageRankList() {
		ArrayList<WebDocument> allDocuments = coordinator.getAll();

		String output = "";
		
		for (WebDocument doc : allDocuments) {
			output += "<table>";
			output += "<tr>";
			output += "<th>URL</th>";
			output += "<th>PageRank Score</th>";
			output += "</tr>";
			
			output += "<tr>";
			output += "<td>url</td>";
			output += "<td><a href=\"" + doc.getURL().toString() + "\">" + doc.getURL().toString() + "</a></td>";
			output += "</tr>";
			
			output += "<tr>";
			output += "<td>content</td>";
			output += "<td>" + doc.getPageRankScore() + "</td>";
			output += "</tr>";
			
			output += "</table>";
		}
		
		return HTMLFrameGenerator.wrapInHTMLFrame("PageRank", output);
	}
	
	public static void reIndexWithBoost() {
		
	}
	
	public static void reIndexRemovingBoost() {
		
	}
}
