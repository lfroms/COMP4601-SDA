package edu.carleton.comp4601.resources.SearchableDocumentArchive;

import java.util.Optional;

import org.json.JSONObject;

import edu.carleton.comp4601.models.WebDocument;
import edu.carleton.comp4601.store.DataCoordinator;

final class DocumentInterface {
	private final static DataCoordinator coordinator = DataCoordinator.getInstance();
	
	public final static String getSingleJSON(Integer id) {
		Optional<WebDocument> document = coordinator.find(id);
		
		if (document.isEmpty()) {
			return "";
		}
		
		WebDocument unwrappedDocument = document.get();

		JSONObject output = new JSONObject();
		
		output
			.put("id", unwrappedDocument.getId())
			.put("name", "some name")
			.put("url", unwrappedDocument.getURL())
			.put("content", unwrappedDocument.getContent());
		
		return output.toString();
	}
	
	public final static String getSingleHTML(Integer id) {
		Optional<WebDocument> document = coordinator.find(id);
		
		if (document.isEmpty()) {
			return "";
		}
		
		WebDocument unwrappedDocument = document.get();
		
		String output = "";
		
		output += "<table>";
		output += "<tr>";
		output += "<th>Column</th>";
		output += "<th>Value</th>";
		output += "</tr>";

		output += "<tr>";
		output += "<td>id</td>";
		output += "<td>" + unwrappedDocument.getId() + "</td>";
		output += "</tr>";
		
		output += "<tr>";
		output += "<td>name</td>";
		output += "<td>" + "some name" + "</td>";
		output += "</tr>";
		
		output += "<tr>";
		output += "<td>url</td>";
		output += "<td><a href=\"" + unwrappedDocument.getURL().toString() + "\">" + unwrappedDocument.getURL().toString() + "</a></td>";
		output += "</tr>";
		
		output += "<tr>";
		output += "<td>content</td>";
		output += "<td>" + unwrappedDocument.getContent() + "</td>";
		output += "</tr>";
		
		output += "</table>";

		return output;		
	}

	public static void delete(Integer id) {
		coordinator.delete(id);
	}
}
