package edu.carleton.comp4601.models;

import org.json.JSONObject;

import edu.uci.ics.crawler4j.url.WebURL;

public final class BinaryDocument extends WebDocument {
	private String mimeType;
	private Long sizeInBytes;
	private String inferredContent;

	public BinaryDocument(
			Integer id,
			Integer parentId,
			WebURL url,
			Integer lastCrawledTime,
			Double pageRankScore) {

		super(id, parentId, url, lastCrawledTime, pageRankScore);
		// TODO Auto-generated constructor stub
	}

	// JSON SERIALIZATION ===============================================================

	public BinaryDocument(JSONObject object) {
			super(object);
			
			this.mimeType = object.getString(Fields.MIME_TYPE);
			this.sizeInBytes = object.getLong(Fields.SIZE_IN_BYTES);
			this.inferredContent = object.getString(Fields.INFERRED_CONTENT);
		}

	public JSONObject toJSON() {
		JSONObject object = super.toJSON();

		object
			.put(Fields.MIME_TYPE, mimeType)
			.put(Fields.SIZE_IN_BYTES, sizeInBytes)
			.put(Fields.INFERRED_CONTENT, inferredContent);

		return object;
	}

	// FIELD NAMES ======================================================================

	private static class Fields {
		public static final String MIME_TYPE = "mime";
		public static final String SIZE_IN_BYTES = "size";
		public static final String INFERRED_CONTENT = "content";
	}

	public static final String TYPE_NAME = "binary";

	@Override
	public String getTypeName() {
		return TYPE_NAME;
	}
}
