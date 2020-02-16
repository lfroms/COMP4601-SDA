package edu.carleton.comp4601.crawler;

import java.io.ByteArrayInputStream;
import java.util.regex.Pattern;

import edu.carleton.comp4601.models.BinaryDocument;
import edu.carleton.comp4601.models.HypertextDocument;
import edu.carleton.comp4601.models.WebDocument;
import edu.carleton.comp4601.store.DataCoordinator;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

final class Crawler extends WebCrawler {
	private final static DataCoordinator dataCoordinator = DataCoordinator.getInstance();
	
	private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|mp3|mp4|zip|gz))$");

	private String[] supportedUrls = {
			"https://sikaman.dyndns.org"
	};
	
	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		String href = url.getURL().toLowerCase();

		return !FILTERS.matcher(href).matches() && stringStartsWithSupportedPrefix(href);
	}

	@Override
	public void visit(Page page) {
		Boolean isHypertextDocument = isHypertextDocument(page);
		
		if (isHypertextDocument) {
			handleHypertextDocument(page);
		} else {
			handleMiscellaneousDocuments(page);
		}
	}
	
	// PRIVATE HELPERS ==================================================================
	
	private void handleHypertextDocument(Page page) {
		HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
		
		if (htmlParseData instanceof HtmlParseData == false) {
			return;
		}
		
		WebURL webUrl = page.getWebURL();

		WebDocument vertex = new HypertextDocument(
				webUrl.getDocid(),
				webUrl,
				getCurrentUnixTimestamp(),
				0.0,
				htmlParseData.getHtml()
			);
		
		dataCoordinator.upsert(vertex);
	}
	
	private void handleMiscellaneousDocuments(Page page) {
		WebURL webUrl = page.getWebURL();
		
		byte[] contentData = page.getContentData();
		ByteArrayInputStream dataStream = new ByteArrayInputStream(contentData);
		TikaHelper tikaHelper = new TikaHelper(dataStream);


		WebDocument vertex = new BinaryDocument(
				webUrl.getDocid(),
				webUrl,
				getCurrentUnixTimestamp(),
				0.0,
				tikaHelper.getMimeType(),
				contentData.length,
				tikaHelper.createInferredContent()
			);
		
		dataCoordinator.upsert(vertex);
	}
	
	private Boolean isHypertextDocument(Page page) {
		return page.getContentType().contains("html");
	}
	
	private Boolean stringStartsWithSupportedPrefix(String input) {
		boolean result = false; 
		  
        for (int i = 0; i < supportedUrls.length; i++) { 
            if (input.startsWith(supportedUrls[i])) {
                result = true; 
                break; 
            } 
        } 
        
        return result;
	}

	private final Integer getCurrentUnixTimestamp() {
		long unixTime = System.currentTimeMillis() / 1000L;
		return (int) unixTime;
	}
}
