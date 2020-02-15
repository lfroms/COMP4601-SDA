package edu.carleton.comp4601.resources.SearchableDocumentArchive;

import java.io.File;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

final class SDACrawlController {
	private static final int NUM_CRAWLERS = 2;
	private static final int MAX_DEPTH = 4;
	private static final int POLITENESS_DELAY = 10;
	private static final int MAX_PAGES = 300;
	private static final String STORAGE_PATH = new File(System.getProperty("user.home"), "/Desktop/crawler.nosync").toString();
	
	public static void main(String[] args) throws Exception {
        CrawlConfig config = getCrawlConfig();

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        controller.addSeed("https://weather.gc.ca/city/pages/on-118_metric_e.html");
        controller.addSeed("https://lowpolycrafts.nz");

        CrawlController.WebCrawlerFactory<Crawler> factory = Crawler::new;
        controller.start(factory, NUM_CRAWLERS);
        
	}
	
	private static CrawlConfig getCrawlConfig() {
		CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(STORAGE_PATH);
        config.setMaxDepthOfCrawling(MAX_DEPTH);
        config.setPolitenessDelay(POLITENESS_DELAY);
        config.setMaxPagesToFetch(MAX_PAGES);
        
        return config;
	}
}
