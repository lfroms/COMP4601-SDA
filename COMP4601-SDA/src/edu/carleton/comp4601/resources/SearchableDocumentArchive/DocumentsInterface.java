package edu.carleton.comp4601.resources.SearchableDocumentArchive;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

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
				.put("name", "some name")
				.put("url", doc.getURL())
				.put("content", doc.getContent());
			
			output.put(singleJSON);
		}
		
		return output.toString();
	}
	
	public final static String getMultiHTML() {
		ArrayList<WebDocument> allDocuments = coordinator.getAll();

		String output = "<html><body>";
		
		for (WebDocument doc : allDocuments) {
			output += "<table>";
			output += "<tr>";
			output += "<th>Column</th>";
			output += "<th>Value</th>";
			output += "</tr>";
	
			output += "<tr>";
			output += "<td>id</td>";
			output += "<td>" + doc.getId() + "</td>";
			output += "</tr>";
			
			output += "<tr>";
			output += "<td>name</td>";
			output += "<td>" + "some name" + "</td>";
			output += "</tr>";
			
			output += "<tr>";
			output += "<td>url</td>";
			output += "<td><a href=\"" + doc.getURL().toString() + "\">" + doc.getURL().toString() + "</a></td>";
			output += "</tr>";
			
			output += "<tr>";
			output += "<td>content</td>";
			output += "<td>" + doc.getContent() + "</td>";
			output += "</tr>";
			
			output += "</table>";
		}
		
		output += "</body></html>";

		return output;		
	}

	public static void delete(Integer id) {
		coordinator.delete(id);
	}
}
