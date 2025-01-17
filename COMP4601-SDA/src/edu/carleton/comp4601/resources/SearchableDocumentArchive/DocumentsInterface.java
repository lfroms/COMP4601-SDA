package edu.carleton.comp4601.resources.SearchableDocumentArchive;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.carleton.comp4601.helpers.HTMLFrameGenerator;
import edu.carleton.comp4601.models.WebDocument;
import edu.carleton.comp4601.store.DataCoordinator;

final class DocumentsInterface {
	private final static DataCoordinator coordinator = DataCoordinator.getInstance();
	
	public final static String getMultiJSON() {
		ArrayList<WebDocument> allDocuments = coordinator.getAll();

		JSONArray output = new JSONArray();
		
		for (WebDocument doc : allDocuments) {
			JSONObject singleJSON = new JSONObject();
			
			singleJSON
				.put("id", doc.getId())
				.put("name", doc.getTitle())
				.put("url", doc.getURL())
				.put("content", doc.getContent());
			
			output.put(singleJSON);
		}
		
		return output.toString();
	}
	
	public final static String getMultiHTML() {
		ArrayList<WebDocument> allDocuments = coordinator.getAll();

		String output = "";
		output += "<table class=\"w3-table-all w3-centered\">";
		output += "<tr>";
		output += "<th>Column</th>";
		output += "<th>Value</th>";
		output += "</tr>";

		
		for (WebDocument doc : allDocuments) {
			output += "<tr>";
			output += "<td>id</td>";
			output += "<td>" + doc.getId() + "</td>";
			output += "</tr>";
			
			output += "<tr>";
			output += "<td>name</td>";
			output += "<td>" + doc.getTitle() + "</td>";
			output += "</tr>";
			
			output += "<tr>";
			output += "<td>url</td>";
			output += "<td><a href=\"" + doc.getURL().toString() + "\">" + doc.getURL().toString() + "</a></td>";
			output += "</tr>";
			
			output += "<tr>";
			output += "<td>content</td>";
			output += "<td>" + doc.getContent() + "</td>";
			output += "</tr>";
		}
		
		output += "</table>";


		return HTMLFrameGenerator.wrapInHTMLFrame("All Documents", output);
	}
	
	public final static String getMultiXML() {
		ArrayList<WebDocument> allDocuments = coordinator.getAll();

		String output = "<documents>";
		
		for (WebDocument doc : allDocuments) {
			output += "<document>";
			
			output += "<id>" + doc.getId() + "</id>";
			output += "<name>" + doc.getTitle() + "</name>";
			output += "<url>" + doc.getURL().toString() + "</url>";
			output += "<content>" + doc.getContent() + "</content>";
			
			output += "</document>";
		}
		
		output += "</documents>";

		return output;		
	}

	public static void delete(Integer id) {
		coordinator.delete(id);
	}

	public static boolean deleteByQuery(String query) {
		return coordinator.deleteByQuery(query);
	}
}
