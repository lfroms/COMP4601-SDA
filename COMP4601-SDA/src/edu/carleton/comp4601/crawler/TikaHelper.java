package edu.carleton.comp4601.crawler;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;

final class TikaHelper {	
	private final static Tika tika = new Tika();
	private final ByteArrayInputStream stream;
	
	public TikaHelper(ByteArrayInputStream stream) {
		this.stream = stream;
		tika.setMaxStringLength(1_000_000_000);
	}
	
	public String createInferredContent() {
		BodyContentHandler handler = new BodyContentHandler();
		AutoDetectParser parser = new AutoDetectParser();
		Metadata metadata = new Metadata();
		
		try {
			parser.parse(stream, handler, metadata);
			return cleanUpWhitespace(handler.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String getMimeType() {
		try {
			return tika.detect(stream);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// PRIVATE HELPERS ==================================================================
	
	private String cleanUpWhitespace(String input) {
		return input.replaceAll("\\s+", " ");
	}
}
