package edu.carleton.comp4601.resources.SearchableDocumentArchive;

import edu.carleton.comp4601.store.DataCoordinator;

final class MainInterface {
	private final static DataCoordinator coordinator = DataCoordinator.getInstance();

	public static void resetAll() {
		coordinator.reset();
	}

}
