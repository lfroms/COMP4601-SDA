package edu.carleton.comp4601.models;

import java.io.ByteArrayInputStream;

import org.json.JSONObject;

import edu.carleton.comp4601.resources.SearchableDocumentArchive.TikaHelper;
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
			byte[] data) {

		super(id, url, lastCrawledTime, pageRankScore);
		
		
		ByteArrayInputStream dataStream = new ByteArrayInputStream(data);
		TikaHelper tikaHelper = new TikaHelper(dataStream);

		this.mimeType = tikaHelper.getMimeType();
		this.sizeInBytes = data.length;
		this.inferredContent = tikaHelper.createInferredContent();
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
