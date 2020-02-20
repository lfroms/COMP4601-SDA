package edu.carleton.comp4601.models;

import org.json.JSONObject;

import edu.uci.ics.crawler4j.url.WebURL;

public final class BinaryDocument extends WebDocument {
	private String mimeType;
	private Integer sizeInBytes;
	private String inferredContent;

	public BinaryDocument(
			Integer id,
			WebURL url,
			Integer lastCrawledTime,
			Double pageRankScore,
			String mimeType,
			Integer sizeInBytes,
			String inferredContent) {

		super(id, url, lastCrawledTime, pageRankScore);
		
		this.mimeType = mimeType;
		this.sizeInBytes = sizeInBytes;
		this.inferredContent = inferredContent;
	}

	// JSON SERIALIZATION ===============================================================

	public BinaryDocument(JSONObject object) {
			super(object);
			
			this.mimeType = object.getString(Fields.MIME_TYPE);
			this.sizeInBytes = object.getInt(Fields.SIZE_IN_BYTES);
			this.inferredContent = object.getString(Fields.INFERRED_CONTENT);
		}

	@Override
	public JSONObject toJSON() {
		JSONObject object = super.toJSON();

		object
			.put(Fields.MIME_TYPE, mimeType)
			.put(Fields.SIZE_IN_BYTES, sizeInBytes)
			.put(Fields.INFERRED_CONTENT, inferredContent);

		return object;
	}
	
	// GETTERS ==========================================================================
	
	public String getMimeType() {
		return mimeType;
	}
	
	public Integer getSize() {
		return sizeInBytes;
	}
	
	@Override
	public String getContent() {
		return inferredContent;
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
