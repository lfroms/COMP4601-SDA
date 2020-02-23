# Searchable Document Archive
* Lukas Romsicki
* Britta Evans-Fenton

## Preliminary
The database, graph, and Lucene index must be loaded with data first for the app to function correctly.
1. Start a MongoDB server at `localhost` with port `27017`.
2. Open the project in Eclipse.
3. Right-click on the project root, then click `Gradle > Refresh Gradle Project`.  You must have a working internet connection for this to work.
    * This step will download all the necessary dependencies to your machine.
4. Go to `Project > Clean` to clean the project of any possible artifacts.

## Performing a crawl

1. Right-click on `CrawlerController.java` in the Project Explorer, then click `Run as > Java Application`. 
2. Wait until the crawl completes.  A completion message will be printed to the Eclipse console.

## Starting the server

You can either:
a) Deploy the _war_ file, or;
b) Launch the server through Eclipse.

Through Eclipse:
_Assuming you still have the Eclipse project open as per the **Preliminary** section._
1. Open a server resource file, for example, `MainResource.java`.
2. Click the green "play" button in the Eclipse toolbar.
3. Wait for the server to launch.

## Starting the client
_Note: You must have a Mac with the latest version of Xcode for this to work._
1. Open the Xcode project from `SDAClient`.
2. Select a target (either Simulator or physical device).
3. Click the "Run" button in the toolbar.

You can then use the search bar to search.  A debounce of 2 seconds has been implemented.  That is, the client waits 2 seconds after the user has finished typing before submitting the request to the server, allowing for the UI to not be locked.

If you are running on a physical device, you may want to change the address of the server.  To do this:
1. Click the "gear" icon at the top/
2. Enter the host (and port) of the server in the presented sheet.
3. Close the sheet and perform a search as per normal.
