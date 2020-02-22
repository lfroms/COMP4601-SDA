package edu.carleton.comp4601.resources.SearchableDocumentArchive;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.carleton.comp4601.dao.Document;
import edu.carleton.comp4601.dao.DocumentCollection;
import edu.carleton.comp4601.helpers.DocumentScoreComparator;
import edu.carleton.comp4601.helpers.HTMLFrameGenerator;
import edu.carleton.comp4601.store.DataCoordinator;

final class SearchInterface {
	private final static DataCoordinator coordinator = DataCoordinator.getInstance();
	
	public final static DocumentCollection searchLocalXML(String terms) {
		return coordinator.search(terms);
	}
	
	public final static DocumentCollection searchDistributedXML(String terms) {
		return coordinator.searchDistributed(terms);
	}

	public final static String searchLocalHTML(String terms) {
		DocumentCollection collection = coordinator.search(terms);
		
		String html = convertCollectionToHTML(collection);
		return HTMLFrameGenerator.wrapInHTMLFrame("Results for: " + terms, html);
	}

	public final static String searchLocalJSON(String terms) {
		DocumentCollection collection = coordinator.search(terms);
	
		return convertCollectionToJSON(collection);
	}
	
	public final static String searchDistributedHTML(String terms) {
		DocumentCollection collection = coordinator.searchDistributed(terms);
		
		String html = convertCollectionToHTML(collection);
		return HTMLFrameGenerator.wrapInHTMLFrame("Results for: " + terms, html);
	}

	public final static String searchDistributedJSON(String terms) {
		DocumentCollection collection = coordinator.searchDistributed(terms);
	
		return convertCollectionToJSON(collection);
	}
	
	// PRIVATE HELPERS ==================================================================
	
	private final static String convertCollectionToHTML(DocumentCollection collection) {
		ArrayList<Document> allDocuments = collection.getDocuments();
		
		if (allDocuments.size() == 0) {
			return "<h1>No results</h1>";
		}
		
		String output = "";
		output += "<table class=\"w3-table-all w3-centered\">";
		output += "<tr>";
		output += "<th>ID</th>";
		output += "<th>Title</th>";
		output += "<th>URL</th>";
		output += "<th>Score</th>";
		output += "</tr>";
		
		Collections.sort(allDocuments, new DocumentScoreComparator());
		
		for (Document doc : allDocuments) {
			output += convertDocumentToHTML(doc);
		}
		
		output += "</table>";
		
		return output;
	}
	
	private final static String convertCollectionToJSON(DocumentCollection collection) {
		JSONArray output = new JSONArray();
		
		ArrayList<Document> allDocuments = collection.getDocuments();
		
		if (allDocuments != null && allDocuments.size() > 0) {
			Collections.sort(allDocuments, new DocumentScoreComparator());
		}
		
		for (Document doc : allDocuments) {
			output.put(convertDocumentToJSON(doc));
		}
		
		return output.toString();
	}
	
	private final static String convertDocumentToHTML(Document doc) {
		String output = "";
		
		output += "<tr>";
		output += "<td>" + doc.getId() + "</td>";
		output += "<td>" + doc.getName() + "</td>";
		output += "<td><a href=\"" + doc.getUrl() + "\">" + doc.getUrl() + "</td>";
		output += "<td>" + doc.getScore() + "</td>";
		output += "</tr>";

		return output;
	}
	
	private final static JSONObject convertDocumentToJSON(Document doc) {
		JSONObject output = new JSONObject();
		
		output
			.put("id", doc.getId())
			.put("name", doc.getName())
			.put("url", doc.getUrl())
			.put("score", doc.getScore());
		
		return output;
	}
}
