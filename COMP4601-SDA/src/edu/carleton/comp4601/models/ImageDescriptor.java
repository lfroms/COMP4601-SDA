package edu.carleton.comp4601.models;

import org.json.JSONObject;

class ImageDescriptor {
	String url;
	String altText;
	
	public ImageDescriptor(String url, String altText) {
		this.url = url;
		this.altText = altText;
	}
	
	// MARK: FIELD NAMES
	
	private static class Fields {
		public static final String IMAGE_URL = "url";
		public static final String IMAGE_ALT = "alt";
	}
	
	// JSON SERIALIZATION
	
	public JSONObject toJSON() {
		JSONObject object = new JSONObject();

		object
			.put(Fields.IMAGE_URL, url)
			.put(Fields.IMAGE_ALT, altText);

		return object;
	}
	
	public static ImageDescriptor fromJSONString(String jsonString) {
		JSONObject jsonObject = new JSONObject(jsonString);
		
		ImageDescriptor descriptor = new ImageDescriptor(
				jsonObject.get(Fields.IMAGE_URL).toString(),
				jsonObject.get(Fields.IMAGE_ALT).toString()
			);
		
		return descriptor;
	}
}
