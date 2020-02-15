package edu.carleton.comp4601.models;

public abstract class WebDocument implements Identifiable {
	private Integer id;
	private String url;
	private String title;
	private Integer lastCrawledTime;
	private Double pageRankScore;

	// PUBLIC INTERFACE
	
	public WebDocument(Integer id, String url, String title, Integer lastCrawledTime, Double pageRankScore) {
		this.id = id;
		this.url = url;
		this.title = title;
		this.lastCrawledTime = lastCrawledTime;
		this.pageRankScore = pageRankScore;
	}
	
	public Integer getId() {
		return id;
	}

	public String getURL() {
		return url;
	}

	public String getTitle() {
		return title;
	}

	public Integer getLastCrawledTime() {
		return lastCrawledTime;
	}

	public Double getPageRankScore() {
		return pageRankScore;
	}
	
	// FIELD NAMES
	
	protected static class Fields {
		public static final String ID = "id";
		public static final String URL = "url";
		public static final String TITLE = "title";
		public static final String LAST_CRAWLED_TIME = "last_crawled_time";
		public static final String PAGE_RANK_SCORE = "page_rank_score";
	}
}
