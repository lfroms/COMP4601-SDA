package edu.carleton.comp4601.models;

import org.json.JSONObject;

class ImageDescriptor {
	private String url;
	private String altText;

	public ImageDescriptor(String url, String altText) {
		this.url = url;
		this.altText = altText;
	}
	
	// JSON SERIALIZATION ===============================================================

	public ImageDescriptor(JSONObject object) {
		this(object.getString(Fields.URL), object.getString(Fields.ALT_TEXT));
	}

	public JSONObject toJSON() {
		JSONObject object = new JSONObject();

		object
			.put(Fields.URL, url)
			.put(Fields.ALT_TEXT, altText);

		return object;
	}

	// FIELD NAMES ======================================================================
	
	private static class Fields {
		public static final String URL = "url";
		public static final String ALT_TEXT = "alt";
	}
}
