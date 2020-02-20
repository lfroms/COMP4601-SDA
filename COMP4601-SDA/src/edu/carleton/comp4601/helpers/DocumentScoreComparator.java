package edu.carleton.comp4601.helpers;

import java.util.Comparator;

import edu.carleton.comp4601.dao.Document;

public final class DocumentScoreComparator implements Comparator<Document> {

	@Override
	public int compare(Document o1, Document o2) {
		return o2.getScore().compareTo(o1.getScore());
	}

}
