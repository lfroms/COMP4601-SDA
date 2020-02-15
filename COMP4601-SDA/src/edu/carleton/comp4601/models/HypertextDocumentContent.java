package edu.carleton.comp4601.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

class HypertextDocumentContent {
	public List<String> links;
	public List<ImageDescriptor> images;
	public List<String> paragraphs;
	public List<String> headings;
	
	public HypertextDocumentContent(List<String> links, List<ImageDescriptor> images, List<String> paragraphs, List<String> headings) {
		this.links = links;
		this.images = images;
		this.paragraphs = paragraphs;
		this.headings = headings;
	}
	
	public static HypertextDocumentContent fromHtmlParseData(String htmlParseDataText) {
		Document doc = Jsoup.parse(htmlParseDataText);
		
		List<String> links = getLinksFromDocument(doc);
		List<ImageDescriptor> images = getImagesFromDocument(doc);
		List<String> paragraphs = getParagraphsFromDocument(doc);
		List<String> headings = getHeadingsFromDocument(doc);
		
		return new HypertextDocumentContent(links, images, paragraphs, headings);
	}
	
	// JSON SERIALIZATION
	
	public JSONObject toJSON() {
		JSONObject object = new JSONObject();
		
		JSONArray imagesArray = new JSONArray();
		images.forEach(image -> {
			imagesArray.put(image.toJSON());
		});

		object
			.put(Fields.LINKS, links)
			.put(Fields.IMAGES, imagesArray)
			.put(Fields.PARAGRAPHS, paragraphs)
			.put(Fields.HEADINGS, headings);
		
		return object;
	}
	
	public static HypertextDocumentContent fromJSON(JSONObject object) {
		List<String> parsedLinks = parseJSONStringArray(object, Fields.LINKS);
		List<String> parsedParagraphs = parseJSONStringArray(object, Fields.PARAGRAPHS);
		List<String> parsedHeadings = parseJSONStringArray(object, Fields.HEADINGS);
		
		JSONArray imagesJSON = object.getJSONArray(Fields.IMAGES);
		List<ImageDescriptor> parsedImages = new ArrayList<>();
		
		imagesJSON.forEach(image -> {
			parsedImages.add(ImageDescriptor.fromJSONString(image.toString()));
		});

		return new HypertextDocumentContent(
				parsedLinks,
				parsedImages,
				parsedParagraphs,
				parsedHeadings
			);
	}
	
	// FIELD NAMES
	
	private static class Fields {
		public static final String LINKS = "links";
		public static final String IMAGES = "images";
		public static final String PARAGRAPHS = "paragraphs";
		public static final String HEADINGS = "headings";
	}
	
	// PRIVATE HELPERS
	
	private static List<String> getLinksFromDocument(Document document) {
		Elements linkElements = document.select("a");
		
		return linkElements.eachAttr("abs:href");
	}
	
	private static List<ImageDescriptor> getImagesFromDocument(Document document) {
		Elements imageElements = document.select("img");
		
		List<ImageDescriptor> descriptors = new ArrayList<>();
		
		imageElements.forEach(image -> {
			ImageDescriptor descriptor = new ImageDescriptor(
					image.attr("src"),
					image.attr("alt")
				);

			descriptors.add(descriptor);
		});
		
		return descriptors;
	}
	
	private static List<String> getParagraphsFromDocument(Document document) {
		Elements paragraphElements = document.select("p");
		
		return paragraphElements.eachText();
	}
	
	private static List<String> getHeadingsFromDocument(Document document) {
		Elements headingElements = document.select("h1, h2, h3, h4, h5, h6");
		
		return headingElements.eachText();
	}
	
	private static List<String> parseJSONStringArray(JSONObject object, String fieldName) {
		List<String> parsedValues = new ArrayList<>();
		JSONArray rawValues = object.getJSONArray(fieldName);
		
		for(int i = 0; i < rawValues.length(); i++)
			parsedValues.add(rawValues.getString(i));
		
		return parsedValues;
	}
}
