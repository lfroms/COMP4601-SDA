package edu.carleton.comp4601.models;

import org.json.JSONObject;

final class HypertextDocument extends WebDocument {
	public HypertextDocumentContent hypertextDocumentContent;

	public HypertextDocument(Integer id, String url, String title, Integer lastCrawledTime, Double pageRankScore,
			HypertextDocumentContent hypertextDocumentContent) {
		super(id, url, title, lastCrawledTime, pageRankScore);

		this.hypertextDocumentContent = hypertextDocumentContent;
	}

	// FIELD NAMES

	private final static class Fields extends WebDocument.Fields {
		public static final String CONTENT = "content";
	}

	// JSON SERIALIZATION

	public final JSONObject toJSON() {
		JSONObject object = new JSONObject();

		object
			.put(Fields.ID, this.getId())
			.put(Fields.URL, this.getURL())
			.put(Fields.TITLE, this.getTitle())
			.put(Fields.LAST_CRAWLED_TIME, this.getLastCrawledTime())
			.put(Fields.CONTENT, hypertextDocumentContent.toJSON());

		return object;
	}

	public static HypertextDocument fromJSON(JSONObject object) {
		return new HypertextDocument(
				object.getInt(Fields.ID),
				object.getString(Fields.URL),
				object.getString(Fields.TITLE),
				object.getInt(Fields.LAST_CRAWLED_TIME),
				object.getDouble(Fields.PAGE_RANK_SCORE),
				HypertextDocumentContent.fromJSON(object.getJSONObject(Fields.CONTENT))
			);
	}
}