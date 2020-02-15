package edu.carleton.comp4601.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public final class HypertextDocument extends WebDocument {
	private String title;
	private List<String> links;
	private List<ImageDescriptor> images;
	private List<String> paragraphs;
	private List<String> headings;

	public HypertextDocument(
			Integer id,
			String url,
			Integer lastCrawledTime,
			Double pageRankScore,
			String title,
			String htmlParseDataText) {

		super(id, url, id, pageRankScore);

		Document doc = Jsoup.parse(htmlParseDataText);
		
		this.title = title;
		this.links = getLinksFromDocument(doc);
		this.images = getImagesFromDocument(doc);
		this.paragraphs = getParagraphsFromDocument(doc);
		this.headings = getHeadingsFromDocument(doc);
	}
	
	// JSON SERIALIZATION ===============================================================

	public HypertextDocument(JSONObject object) {
		super(object);
		
		this.links = parseJSONStringArray(object, Fields.LINKS);
		this.paragraphs = parseJSONStringArray(object, Fields.PARAGRAPHS);
		this.headings = parseJSONStringArray(object, Fields.HEADINGS);

		JSONArray imagesJSON = object.getJSONArray(Fields.IMAGES);

		List<ImageDescriptor> parsedImages = new ArrayList<>();
		imagesJSON.forEach(image -> {
			JSONObject imageJSON = new JSONObject(image);
			parsedImages.add(new ImageDescriptor(imageJSON));
		});

		this.images = parsedImages;
	}


	public JSONObject toJSON() {
		JSONObject object = super.toJSON();

		JSONArray imagesArray = new JSONArray();
		images.forEach(image -> {
			imagesArray.put(image.toJSON());
		});

		object
			.put(Fields.TITLE, title)
			.put(Fields.LINKS, links)
			.put(Fields.IMAGES, imagesArray)
			.put(Fields.PARAGRAPHS, paragraphs)
			.put(Fields.HEADINGS, headings);

		return object;
	}

	// HTML PARSING HELPERS =============================================================

	private static List<String> getLinksFromDocument(Document document) {
		Elements linkElements = document.select("a");

		return linkElements.eachAttr("abs:href");
	}

	private static List<ImageDescriptor> getImagesFromDocument(Document document) {
		Elements imageElements = document.select("img");

		List<ImageDescriptor> descriptors = new ArrayList<>();

		imageElements.forEach(image -> {
			ImageDescriptor descriptor = 
					new ImageDescriptor(
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

		for (int i = 0; i < rawValues.length(); i++)
			parsedValues.add(rawValues.getString(i));

		return parsedValues;
	}

	// FIELD NAMES ======================================================================

	private static class Fields {
		public static final String TITLE = "title";
		public static final String LINKS = "links";
		public static final String IMAGES = "images";
		public static final String PARAGRAPHS = "paragraphs";
		public static final String HEADINGS = "headings";
	}
	
	public static final String TYPE_NAME = "hypertext";

	@Override
	public String getTypeName() {
		return TYPE_NAME;
	}
}
