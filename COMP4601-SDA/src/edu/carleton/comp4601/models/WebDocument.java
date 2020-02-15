package edu.carleton.comp4601.models;

import org.json.JSONObject;

import edu.uci.ics.crawler4j.url.WebURL;

public abstract class WebDocument implements Identifiable, Locatable {
	private Integer id;
	private Integer parentId;
	private static String type;
	private WebURL url;
	private Integer lastCrawledTime;
	private Double pageRankScore;
		
	public WebDocument(Integer id, Integer parentId, WebURL url, Integer lastCrawledTime, Double pageRankScore) {
		this.id = id;
		this.parentId = parentId;
		this.url = url;
		this.lastCrawledTime = lastCrawledTime;
		this.pageRankScore = pageRankScore;
	}
	
	// JSON SERIALIZATION ===============================================================
	
	public WebDocument(JSONObject object) {
		this(
				object.getInt(Fields.ID),
				object.getInt(Fields.PARENT_ID),
				null,
				object.getInt(Fields.LAST_CRAWLED_TIME),
				object.getDouble(Fields.PAGE_RANK_SCORE)
			);
		
		WebURL newUrl = new WebURL();
		newUrl.setURL(object.getString(Fields.URL));
		newUrl.setDocid(this.id);
		newUrl.setParentDocid(this.parentId);
		
		this.url = newUrl;
	}
	
	public JSONObject toJSON() {
		JSONObject object = new JSONObject();
		
		object
			.put(Fields.ID, id)
			.put(Fields.PARENT_ID, parentId)
			.put(TYPE_FIELD, type)
			.put(Fields.URL, url)
			.put(Fields.LAST_CRAWLED_TIME, lastCrawledTime)
			.put(Fields.PAGE_RANK_SCORE, pageRankScore);
		
		return object;
	}
	
	// GETTERS ==========================================================================
	
	public Integer getId() {
		return id;
	}
	
	public Integer getParentId() {
		return parentId;
	}

	public WebURL getURL() {
		return url;
	}

	public Integer getLastCrawledTime() {
		return lastCrawledTime;
	}

	public Double getPageRankScore() {
		return pageRankScore;
	}
	
	public abstract String getTypeName();
	
	// FIELD NAMES ======================================================================
	
	private static class Fields {
		public static final String ID = "id";
		public static final String PARENT_ID = "parent_id";
		public static final String URL = "url";
		public static final String LAST_CRAWLED_TIME = "last_crawled_time";
		public static final String PAGE_RANK_SCORE = "page_rank_score";
	}
	
	public static final String TYPE_FIELD = "type";
}
