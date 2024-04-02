package org.eu.miraikan.aiyou.model.gemini.semanticRetrieval;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import org.eu.miraikan.aiyou.model.gemini.GeminiAdapter;
import org.eu.miraikan.aiyou.model.gemini.semanticRetrieval.template.*;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RetrievalAdapter extends GeminiAdapter {


    public RetrievalAdapter(Map<String, String> config) {
        super(config, null);
    }

    public RetrievalAdapter(Map<String, String> config,String modelName) {
        super(config, modelName);
    }

    public HttpRequest createCorpusRestRequest(Corpus corpus) throws JsonProcessingException {

        String json = objectMapper.writeValueAsString(corpus);

        HttpRequest httpRequest = HttpRequest.newBuilder ()
                .uri(URI.create(BASE_URL+"/v1beta/corpora"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+ACCESS_TOKEN)
                .header("X-goog-user-project", PROJECT_ID)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        return httpRequest;
    }

    public HttpRequest getCorpusRestRequest(String name){
        HttpRequest httpRequest = HttpRequest.newBuilder ()
                .uri(URI.create(BASE_URL+"/v1beta/"+name))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+ACCESS_TOKEN)
                .header("X-goog-user-project", PROJECT_ID)
                .GET()
                .build();
        return httpRequest;

    }

    public Corpus handleCorpusResponse(HttpResponse<String> httpResponse) throws JsonProcessingException {
        Corpus corpus = objectMapper.readValue(httpResponse.body(), Corpus.class);
        return corpus;
    }

    public HttpRequest createDeleteCorpusRestRequest(String name) {
        HttpRequest httpRequest = HttpRequest.newBuilder ()
                .uri(URI.create(BASE_URL+"/v1beta/"+name))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+ACCESS_TOKEN)
                .header("X-goog-user-project", PROJECT_ID)
                .DELETE()
                .build();
        return httpRequest;

    }

    public HttpRequest createListCorpusRestRequest() {
        HttpRequest httpRequest = HttpRequest.newBuilder ()
                .uri(URI.create(BASE_URL+"/v1beta/corpora"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+ACCESS_TOKEN)
                .header("X-goog-user-project", PROJECT_ID)
                .GET()
                .build();
        return httpRequest;
    }

    public List<Corpus> handleListCorpusResponse(HttpResponse<String> httpResponse) throws JsonProcessingException {
        if(httpResponse.statusCode()==200&&(
                httpResponse.body()==null||httpResponse.body().equals("")||"{}".equals(httpResponse.body().trim()))){
            return Collections.emptyList();
        }
        JsonNode rootNode = objectMapper.readTree(httpResponse.body());
        JsonNode jsonNode = rootNode.get("corpora");
        List<Corpus> corpora =  objectMapper.readValue(jsonNode.toString(), new TypeReference<>() {
        });
        return corpora;
    }

    public HttpRequest createDocumentRestRequest(String parent, Document document) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(document);

        HttpRequest httpRequest = HttpRequest.newBuilder ()
                .uri(URI.create(BASE_URL+"/v1beta/"+parent+"/documents"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+ACCESS_TOKEN)
                .header("X-goog-user-project", PROJECT_ID)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        return httpRequest;
    }

    public Document handleDocumentResponse(HttpResponse<String> httpResponse) throws JsonProcessingException {
      return   objectMapper.readValue(httpResponse.body(), Document.class);
    }

    public HttpRequest getDocumentRestRequest(String name) {
        HttpRequest httpRequest = HttpRequest.newBuilder ()
                .uri(URI.create(BASE_URL+"/v1beta/"+name))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+ACCESS_TOKEN)
                .header("X-goog-user-project", PROJECT_ID)
                .GET()
                .build();
        return httpRequest;
    }

    public HttpRequest createDeleteDocumentRestRequest(String name) {
        HttpRequest httpRequest = HttpRequest.newBuilder ()
                .uri(URI.create(BASE_URL+"/v1beta/"+name))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+ACCESS_TOKEN)
                .header("X-goog-user-project", PROJECT_ID)
                .DELETE()
                .build();
        return httpRequest;
    }

    public HttpRequest createListDocumentRestRequest(String parent) {
        HttpRequest httpRequest = HttpRequest.newBuilder ()
                .uri(URI.create(BASE_URL+"/v1beta/"+parent+"/documents"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+ACCESS_TOKEN)
                .header("X-goog-user-project", PROJECT_ID)
                .GET()
                .build();
        return httpRequest;
    }

    public List<Document> handleListDocumentResponse(HttpResponse<String> httpResponse) throws JsonProcessingException {

        if(httpResponse.statusCode()==200&&(
                httpResponse.body()==null||httpResponse.body().equals("")||"{}".equals(httpResponse.body().trim()))){
            return Collections.emptyList();
        }
        JsonNode rootNode = objectMapper.readTree(httpResponse.body());
        JsonNode documentsNode = rootNode.get("documents");
        return objectMapper.readValue(documentsNode.toString(), new TypeReference<>() {
        });
    }

    public HttpRequest createChunkRestRequest(String parent, Chunk chunk) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(chunk);

        HttpRequest httpRequest = HttpRequest.newBuilder ()
                .uri(URI.create(BASE_URL+"/v1beta/"+parent+"/chunks"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+ACCESS_TOKEN)
                .header("X-goog-user-project", PROJECT_ID)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        return httpRequest;

    }

    public Chunk handleChunkResponse(HttpResponse<String> httpResponse) throws JsonProcessingException {
        return objectMapper.readValue(httpResponse.body(), Chunk.class);
    }

    public HttpRequest batchCreateChunksRestRequest(String parent, List<CreateChunkRequest> requests) throws
            JsonProcessingException{
        String json = objectMapper.writeValueAsString(requests);
        HttpRequest httpRequest = HttpRequest.newBuilder ()
                .uri(URI.create(BASE_URL+"/v1beta/"+parent+"/chunks:batchCreate"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+ACCESS_TOKEN)
                .header("X-goog-user-project", PROJECT_ID)
                .POST(HttpRequest.BodyPublishers.ofString("{requests:"+json+"}"))
                .build();
        return httpRequest;

    }

    public List<Chunk> handleBatchCreateChunksResponse(HttpResponse<String> httpResponse) throws JsonProcessingException {
        if(httpResponse.statusCode()==200&&(
                httpResponse.body()==null||httpResponse.body().equals("")||"{}".equals(httpResponse.body().trim()))){
            return Collections.emptyList();
        }
        JsonNode rootNode = objectMapper.readTree(httpResponse.body());
        JsonNode chunksNode = rootNode.get("chunks");

        return objectMapper.readValue(chunksNode.toString(), new TypeReference<>(){});
    }


    public HttpRequest createDeleteChunkRestRequest(String name) {
        HttpRequest httpRequest = HttpRequest.newBuilder ()
                .uri(URI.create(BASE_URL+"/v1beta/"+name))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+ACCESS_TOKEN)
                .header("X-goog-user-project", PROJECT_ID)
                .DELETE()
                .build();
        return httpRequest;
    }

    public HttpRequest getChunkRestRequest(String name) {
        HttpRequest httpRequest = HttpRequest.newBuilder ()
                .uri(URI.create(BASE_URL+"/v1beta/"+name))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+ACCESS_TOKEN)
                .header("X-goog-user-project", PROJECT_ID)
                .GET()
                .build();
        return httpRequest;
    }

    public HttpRequest createListChunksRestRequest(String parent) {
        HttpRequest httpRequest = HttpRequest.newBuilder ()
                .uri(URI.create(BASE_URL+"/v1beta/"+parent+"/chunks"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+ACCESS_TOKEN)
                .header("X-goog-user-project", PROJECT_ID)
                .GET()
                .build();
        return httpRequest;
    }

    public List<Chunk> handleListChunksResponse(HttpResponse<String> httpResponse) throws JsonProcessingException {
        if(httpResponse.statusCode()==200&&(
                httpResponse.body()==null||httpResponse.body().equals("")||"{}".equals(httpResponse.body().trim()))){
            return Collections.emptyList();
        }
        JsonNode rootNode = objectMapper.readTree(httpResponse.body());
        JsonNode chunksNode = rootNode.get("chunks");
        return  objectMapper.readValue(chunksNode.toString(), new TypeReference<>() {
        });
    }

    public HttpRequest createQueryRestRequest(String name, QueryRequest queryRequest) throws JsonProcessingException {

        String json = objectMapper.writeValueAsString(queryRequest);

        HttpRequest httpRequest = HttpRequest.newBuilder ()
                .uri(URI.create(BASE_URL+"/v1beta/"+name+":query"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+ACCESS_TOKEN)
                .header("X-goog-user-project", PROJECT_ID)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        return httpRequest;
    }

    public List<RelevantChunk> handleQueryResponse(HttpResponse<String> httpResponse) throws JsonProcessingException {
        if(httpResponse.statusCode()==200&&(
                httpResponse.body()==null||httpResponse.body().equals("")||"{}".equals(httpResponse.body().trim()))){
            return Collections.emptyList();
        }
        JsonNode rootNode = objectMapper.readTree(httpResponse.body());
        JsonNode jsonNode = rootNode.get("relevantChunks");
        return objectMapper.readValue(jsonNode.toString(), new TypeReference<List<RelevantChunk>>() {
        });
    }
}
