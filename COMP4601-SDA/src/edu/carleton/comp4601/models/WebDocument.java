package edu.carleton.comp4601.models;

import org.json.JSONObject;

public abstract class WebDocument implements Identifiable {
	private Integer id;
	private String url;
	private Integer lastCrawledTime;
	private Double pageRankScore;
	
	public WebDocument(Integer id, String url, Integer lastCrawledTime, Double pageRankScore) {
		this.id = id;
		this.url = url;
		this.lastCrawledTime = lastCrawledTime;
		this.pageRankScore = pageRankScore;
	}
	
	// JSON SERIALIZATION ===============================================================
	
	public WebDocument(JSONObject object) {
		this.id = object.getInt(Fields.ID);
		this.url = object.getString(Fields.URL);
		this.lastCrawledTime = object.getInt(Fields.LAST_CRAWLED_TIME);
		this.pageRankScore = object.getDouble(Fields.PAGE_RANK_SCORE);
	}
	
	public JSONObject toJSON() {
		JSONObject object = new JSONObject();
		
		object
			.put(Fields.ID, id)
			.put(Fields.URL, url)
			.put(Fields.LAST_CRAWLED_TIME, lastCrawledTime)
			.put(Fields.PAGE_RANK_SCORE, pageRankScore);
		
		return object;
	}
	
	// GETTERS ==========================================================================
	
	public Integer getId() {
		return id;
	}

	public String getURL() {
		return url;
	}

	public Integer getLastCrawledTime() {
		return lastCrawledTime;
	}

	public Double getPageRankScore() {
		return pageRankScore;
	}
	
	// FIELD NAMES ======================================================================
	
	private static class Fields {
		public static final String ID = "id";
		public static final String URL = "url";
		public static final String LAST_CRAWLED_TIME = "last_crawled_time";
		public static final String PAGE_RANK_SCORE = "page_rank_score";
	}
}
