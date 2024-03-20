package org.eu.miraikan.aiyou.model.gemini.tuning;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import org.eu.miraikan.aiyou.model.gemini.GeminiAdapter;
import org.eu.miraikan.aiyou.types.tuning.TunedModel;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

/**
 * Tuning functions can only be accessed via OAuth
 */
public class GeminiTuningAdapter extends GeminiAdapter {
    public GeminiTuningAdapter(Map<String, String> config, String modelName) {
        super(config, modelName);
    }


    public HttpRequest createListTunedModelsRequest(){

        return HttpRequest.newBuilder ()
                .uri(URI.create(BASE_URL+"/v1beta/tunedModels"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+ACCESS_TOKEN)
                .header("X-goog-user-project", PROJECT_ID)
                .GET()
                .build();
    }

    public List<TunedModel> handleListTunedModelsResponse(HttpResponse<String> httpResponse) throws JsonProcessingException {
        TypeReference<Map<String, List<TunedModel>>> typeRef = new TypeReference<Map<String, List<TunedModel>>>() {};
        Map<String, List<TunedModel>> map = objectMapper.readValue(httpResponse.body(), typeRef);
        List<TunedModel> list = map.get("tunedModels");
        return list;
    }

    public HttpRequest createTunedModelRequest(TunedModel tunedModel) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(tunedModel);
        HttpRequest httpRequest = HttpRequest.newBuilder ()
                .uri(URI.create(BASE_URL+"/v1beta/tunedModels"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+ACCESS_TOKEN)
                .header("X-goog-user-project", PROJECT_ID)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        return httpRequest;
    }

    public String handleCreateTunedModelResponse(HttpResponse<String> httpResponse) throws JsonProcessingException {

        JsonNode rootNode = objectMapper.readTree(httpResponse.body());
        JsonNode tunedModelNode = rootNode.get("metadata").get("tunedModel");

        return tunedModelNode.asText();
    }

    public HttpRequest createGetModelRequest(String modelName) {

        return HttpRequest.newBuilder ()
                .uri(URI.create(BASE_URL+"/v1beta/"+modelName))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+ACCESS_TOKEN)
                .header("X-goog-user-project", PROJECT_ID)
                .GET()
                .build();
    }

    public TunedModel handleGetModelRequest(HttpResponse<String> httpResponse) throws JsonProcessingException {

        return objectMapper.readValue(httpResponse.body(),TunedModel.class);
    }

    public HttpRequest createDeleteModelRequest(String modelName) {
        return HttpRequest.newBuilder ()
                .uri(URI.create(BASE_URL+"/v1beta/"+modelName))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+ACCESS_TOKEN)
                .header("X-goog-user-project", PROJECT_ID)
                .DELETE()
                .build();

    }
}
