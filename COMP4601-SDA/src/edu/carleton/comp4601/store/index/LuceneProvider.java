package edu.carleton.comp4601.store.index;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.queryparser.classic.QueryParser;

import edu.carleton.comp4601.dao.DocumentCollection;
import edu.carleton.comp4601.models.Identifiable;
import edu.carleton.comp4601.store.DocumentMapper;
import edu.carleton.comp4601.store.MappableProvider;
import edu.carleton.comp4601.store.SearchableAndStorable;

public final class LuceneProvider<DocumentType extends Identifiable> extends MappableProvider<DocumentType, Document>
		implements SearchableAndStorable<DocumentType> {
	
	private static final String INDEX_PATH = new File(System.getProperty("user.home"), "/Desktop/index.nosync").toString();
	
	private final IndexWriter writer;
	private final IndexSearcher searcher;

	public LuceneProvider(Supplier<? extends DocumentMapper<DocumentType, Document>> mapperConstructor) {
		super(mapperConstructor);
		
		try {
			writer = createWriter();
			searcher = createSearcher();

		} catch (IOException e) {
			e.printStackTrace();
			
			throw new RuntimeException("Failed to create Lucene IndexWriter or IndexSearcher");
		}
	}

	@Override
	public void upsert(DocumentType input) {
		Document document = mapperConstructor.get().serialize(input);
		
		try {
			writer.addDocument(document);
			writer.commit();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void bulkUpsert(List<DocumentType> input) {		
		List<Document> documents = new ArrayList<>();
		
		input.forEach(document -> {
			documents.add(mapperConstructor.get().serialize(document));
		});
		
		try {
			writer.addDocuments(documents);
			writer.commit();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Optional<DocumentType> find(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DocumentCollection search(String terms) {
		DocumentCollection output = new DocumentCollection();
		
		try {
			TopDocs topDocs = searchDocuments(terms, searcher);
			
			ArrayList<edu.carleton.comp4601.dao.Document> documents = deserializeScoreDocs(topDocs.scoreDocs);
			output.setDocuments(documents);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return output;
	}
	
	// PRIVATE HELPERS ==================================================================
	
	private static TopDocs searchDocuments(String terms, IndexSearcher searcher) throws Exception {
		// TODO change content to proper field.  Also need to implement fields.
		QueryParser qp = new QueryParser("content", new StandardAnalyzer());
        Query firstNameQuery = qp.parse(terms);
        TopDocs hits = searcher.search(firstNameQuery, 10);
        return hits;
	}
	
	private static IndexWriter createWriter() throws IOException {
		FSDirectory dir = FSDirectory.open(Paths.get(INDEX_PATH));
        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
        IndexWriter writer = new IndexWriter(dir, config);

        return writer;
	}
	
	private static IndexSearcher createSearcher() throws IOException {
		FSDirectory dir = FSDirectory.open(Paths.get(INDEX_PATH));
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		
		return searcher;
	}
	
	private ArrayList<edu.carleton.comp4601.dao.Document> deserializeScoreDocs(ScoreDoc[] scoreDocs) {
		LuceneMapper luceneMapper = (LuceneMapper) mapperConstructor.get();
		ArrayList<edu.carleton.comp4601.dao.Document> documents = new ArrayList<>();
		
		for (ScoreDoc doc : scoreDocs) {
			edu.carleton.comp4601.dao.Document deserializedDocument = luceneMapper.deserialize(doc);
			documents.add(deserializedDocument);
		}
		
		return documents;
	}
}
