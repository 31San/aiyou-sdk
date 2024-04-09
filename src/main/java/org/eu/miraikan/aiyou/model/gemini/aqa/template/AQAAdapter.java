package org.eu.miraikan.aiyou.model.gemini.aqa.template;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.eu.miraikan.aiyou.model.gemini.GeminiAdapter;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class AQAAdapter extends GeminiAdapter {

    public AQAAdapter(Map<String, String> config, String modelName) {
        super(config, modelName);
    }

    /**
     *
     * @param generateAnswerRequest  OAuth is required for semanticRetriever
     * @return
     */
    public HttpRequest createGenerateAnswerRequest(GenerateAnswerRequest generateAnswerRequest) throws JsonProcessingException {
        if(generateAnswerRequest.getSemanticRetriever()!=null&&ACCESS_TOKEN==null){
            throw new UnsupportedOperationException("semanticRetriever only supports OAuth");
        }
        String json = objectMapper.writeValueAsString(generateAnswerRequest);
        HttpRequest request = null;

        if(API_KEY!=null){
            request = HttpRequest.newBuilder ()
                    .uri(URI.create(BASE_URL+"/v1beta/"+MODEL_NAME+":generateAnswer?key="+API_KEY))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
        }else {
            //OAuth
            request = HttpRequest.newBuilder ()
                    .uri(URI.create(BASE_URL+"/v1beta/"+MODEL_NAME+":generateAnswer"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer "+ACCESS_TOKEN)
                    .header("X-goog-user-project", PROJECT_ID)
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
        }


        return request;
    }

    public GenerateAnswerResponse handleGenerateAnswerResponse(HttpResponse<String> httpResponse) throws JsonProcessingException {
        return objectMapper.readValue(httpResponse.body(), GenerateAnswerResponse.class);
    }
}
