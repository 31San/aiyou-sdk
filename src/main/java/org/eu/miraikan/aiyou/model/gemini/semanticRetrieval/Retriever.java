package org.eu.miraikan.aiyou.model.gemini.semanticRetrieval;


import org.eu.miraikan.aiyou.generativeClient.RestChatClient;
import org.eu.miraikan.aiyou.model.gemini.semanticRetrieval.template.*;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Retriever {
    RetrievalAdapter retrievalAdapter;
    RestChatClient client;

    public Retriever(RestChatClient client) {
        this.client = client;
        retrievalAdapter= new RetrievalAdapter(client.getClientConfig());
    }

    public Corpus createCorpus(Optional<String> name, Optional<String> displayName) throws IOException, InterruptedException {

        Corpus corpus = new Corpus();

        name.ifPresent(s -> corpus.setName("corpora/" + s));

        displayName.ifPresent(corpus::setDisplayName);



        HttpRequest httpRequest = retrievalAdapter.createCorpusRestRequest(corpus);
        HttpResponse<String>  httpResponse = client.generateContent(httpRequest);
        return retrievalAdapter.handleCorpusResponse(httpResponse);


    }

    public  Corpus getCorpus(String name) throws IOException, InterruptedException {
        if(name.indexOf('/')==-1){
            name="corpora/"+name;
        }
        HttpRequest httpRequest = retrievalAdapter.getCorpusRestRequest(name);
        HttpResponse<String>  httpResponse = client.generateContent(httpRequest);
        Corpus corpus = retrievalAdapter.handleCorpusResponse(httpResponse);
        return corpus;
    }

    public void deleteCorpus(String name,boolean force) throws IOException, InterruptedException {
        if(name.indexOf('/')==-1){
            name="corpora/"+name;
        }
        if(force){
            name=name+"?force=true";
        }

        HttpRequest httpRequest = retrievalAdapter.createDeleteCorpusRestRequest(name);
        HttpResponse<String>  httpResponse = client.generateContent(httpRequest);
    }

    /**
     * Paging is not supported yet
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public List<Corpus> listCorpora() throws IOException, InterruptedException {
        HttpRequest httpRequest = retrievalAdapter.createListCorpusRestRequest();
        HttpResponse<String>  httpResponse = client.generateContent(httpRequest);
        List<Corpus> corpora = retrievalAdapter.handleListCorpusResponse(httpResponse);
        return corpora;

    }

    /**
     * Corpus name, document instance are required.
     * CustomMetadata is optional
     * @return
     */
    public Document createDocument(String parent, Optional<String> name, Optional<String> displayName
            , Optional<List<CustomMetadata>> customMetadata) throws IOException, InterruptedException {
        Document document = new Document();
        name.ifPresent(s -> document.setName(parent + "/documents/" + s));
        displayName.ifPresent(document::setDisplayName);
        customMetadata.ifPresent(document::setCustomMetadata);
        HttpRequest httpRequest = retrievalAdapter.createDocumentRestRequest(parent,document);
        HttpResponse<String> httpResponse = client.generateContent(httpRequest);

        return retrievalAdapter.handleDocumentResponse(httpResponse);
    }

    /**
     * Use Document name as id.
     * This method won't add prefix automatically
     * Eg: corpora/corpus-123/documents/document-123
     *
     * @param name
     * @return
     */
    public Document getDocument(String name) throws IOException, InterruptedException {


        HttpRequest httpRequest = retrievalAdapter.getDocumentRestRequest(name);
        HttpResponse<String>  httpResponse = client.generateContent(httpRequest);
        return retrievalAdapter.handleDocumentResponse(httpResponse);
    }

    /**
     *
     * @param name name of Corpus or Document for search
     * @param query arbitrary query string
     * @param metadataFilters Filter for Chunk and Document metadata
     * @param resultCount 1 to 100
     * @return RelevantChunk chunk with relevant score
     * @throws IOException
     * @throws InterruptedException
     */
    public List<RelevantChunk> query(String name,String query,Optional<List<MetadataFilter>> metadataFilters,
                      Optional<Integer> resultCount) throws IOException, InterruptedException {
        if(resultCount.isPresent()) {
            int i = resultCount.get();
            if (i > 100 || i < 0) {
                throw new InvalidParameterException();
            }
        }
        QueryRequest queryRequest = new QueryRequest();
        queryRequest.setQuery(query);
        metadataFilters.ifPresent(m->queryRequest.setMetadataFilters(m));
        resultCount.ifPresent(r->queryRequest.setResultsCount(r));
        HttpRequest httpRequest = retrievalAdapter.createQueryRestRequest(name,queryRequest);
        HttpResponse<String> httpResponse = client.generateContent(httpRequest);
        return retrievalAdapter.handleQueryResponse(httpResponse);

    }

    public void deleteDocument(String name,boolean force) throws IOException, InterruptedException {

        if(force){
            name=name+"?force=true";
        }
        HttpRequest httpRequest = retrievalAdapter.createDeleteDocumentRestRequest(name);
        HttpResponse<String>  httpResponse = client.generateContent(httpRequest);
    }

    public List<Document> listDocuments(String parent) throws IOException, InterruptedException {
        if(parent.indexOf('/')==-1){
            parent="corpora/"+parent;
        }

        HttpRequest httpRequest = retrievalAdapter.createListDocumentRestRequest(parent);
        HttpResponse<String>  httpResponse = client.generateContent(httpRequest);
        return retrievalAdapter.handleListDocumentResponse(httpResponse);

    }

    public Chunk createChunk(String parent, String data, Optional<String> name, Optional<List<CustomMetadata>> customMetadata) throws IOException, InterruptedException {
        Chunk chunk = new Chunk();
        chunk.setChunkData(data);
        name.ifPresent(s -> chunk.setName(parent+"/chunks/"+s));
        customMetadata.ifPresent(chunk::setCustomMetadata);
        HttpRequest httpRequest = retrievalAdapter.createChunkRestRequest(parent,chunk);
        HttpResponse<String>  httpResponse = client.generateContent(httpRequest);;
        return  retrievalAdapter.handleChunkResponse(httpResponse);

    }

    public List<Chunk> batchCreateChunks(String parent,List<Chunk> chunks) throws IOException, InterruptedException {
        List<CreateChunkRequest> createChunkRequests = chunks.stream().map(chunk -> {
            CreateChunkRequest createChunkRequest = new CreateChunkRequest();
            createChunkRequest.setParent(parent);
            createChunkRequest.setChunk(chunk);
            return createChunkRequest;
        }).collect(Collectors.toList());

        HttpRequest httpRequest = retrievalAdapter.batchCreateChunksRestRequest(parent,createChunkRequests);
        HttpResponse<String>  httpResponse = client.generateContent(httpRequest);;
        return  retrievalAdapter.handleBatchCreateChunksResponse(httpResponse);


    }

    public Chunk getChunk(String name) throws IOException, InterruptedException {
        HttpRequest httpRequest = retrievalAdapter.getChunkRestRequest(name);
        HttpResponse<String>  httpResponse = client.generateContent(httpRequest);
        return retrievalAdapter.handleChunkResponse(httpResponse);
    }

    public void deleteChunk(String name) throws IOException, InterruptedException {
        HttpRequest httpRequest = retrievalAdapter.createDeleteChunkRestRequest(name);
        HttpResponse<String>  httpResponse = client.generateContent(httpRequest);
    }

    public List<Chunk> listChunks(String parent) throws IOException, InterruptedException {


        HttpRequest httpRequest = retrievalAdapter.createListChunksRestRequest(parent);
        HttpResponse<String>  httpResponse = client.generateContent(httpRequest);
        return retrievalAdapter.handleListChunksResponse(httpResponse);
    }


}
