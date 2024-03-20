package org.eu.miraikan.aiyou.model.gemini;



import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eu.miraikan.aiyou.model.gemini.template.*;

import org.eu.miraikan.aiyou.model.ModelAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;



public class GeminiAdapter implements ModelAdapter<GeminiResponse> {
    protected String API_KEY;

    protected String BASE_URL ="https://generativelanguage.googleapis.com";

    protected String ACCESS_TOKEN;

    protected String PROJECT_ID;

    protected String MODEL_NAME;

    protected ObjectMapper objectMapper;

    public String getModelName() {
        return MODEL_NAME;
    }

    public void setModelName(String modelName) {
        this.MODEL_NAME = modelName;
    }



    public GeminiAdapter(Map<String, String> config,String modelName) {
        if(config.get("API_KEY")!=null){
            API_KEY = config.get("API_KEY");
        }

        if(config.get("ACCESS_TOKEN")!=null){
            ACCESS_TOKEN = config.get("ACCESS_TOKEN");
        }

        if(config.get("PROJECT_ID")!=null){
            PROJECT_ID = config.get("PROJECT_ID");
        }
        BASE_URL = config.get("BASE_URL")==null?BASE_URL:config.get("BASE_URL");


        MODEL_NAME=modelName;
        objectMapper = new ObjectMapper();
    }



   public HttpRequest createHttpRequest(GeminiRequest generativeRequest) throws JsonProcessingException {


        String json = objectMapper.writeValueAsString(generativeRequest);

       HttpRequest request = null;
        //create request
        if(API_KEY!=null){
            request = HttpRequest.newBuilder ()
                    .uri(URI.create(BASE_URL+"/v1beta/models/"+MODEL_NAME+":generateContent?key="+API_KEY))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
        }else {
            //OAuth
            request = HttpRequest.newBuilder ()
                    .uri(URI.create(BASE_URL+"/v1beta/"+MODEL_NAME+":generateContent"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer "+ACCESS_TOKEN)
                    .header("X-goog-user-project", PROJECT_ID)
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
        }





        return request;
    }


    public HttpRequest createStreamRequest( GeminiRequest generativeRequest) throws JsonProcessingException {
        //deal with generativeRequest to json


        String json = objectMapper.writeValueAsString(generativeRequest);

        HttpRequest request = null;
        if(API_KEY!=null){
           request = HttpRequest.newBuilder ()
                    .uri(URI.create(BASE_URL+"/v1beta/models/"+MODEL_NAME+":streamGenerateContent?key="+API_KEY))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
        }else {
            request = HttpRequest.newBuilder ()
                    .uri(URI.create(BASE_URL+"/v1beta/"+MODEL_NAME+":streamGenerateContent"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer "+ACCESS_TOKEN)
                    .header("X-goog-user-project", PROJECT_ID)
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
        }



        return request;
    }

    /**
     * Use Json Stream parser
     */
    @Override
    public  GeminiResponse handleStream(InputStream is){



        JsonFactory jsonFactory = new JsonFactory();
        try {




            JsonParser parser = jsonFactory.createParser(is);

            JsonToken token = parser.nextToken();

            if (token == JsonToken.START_ARRAY) {
                token = parser.nextToken();
            }

            if(token == JsonToken.START_OBJECT){
                GeminiResponse geminiResponse = objectMapper.readValue(parser, GeminiResponse.class);

                int c;
                while ((c=is.read())!=-1){
                    if(c==','){
                        break;
                    }
                }

                return geminiResponse;
            }else {
                return null;
            }


        } catch (IOException e) {
           e.printStackTrace();
        }

        return null;

      //  Pattern pattern = Pattern.compile("\"text\":\\s*\"(.*)\"");


    }

    public GeminiResponse handleHttpResponse(HttpResponse httpResponse) throws JsonProcessingException {
        HttpResponse<String> response = httpResponse;
        ObjectMapper objectMapper = new ObjectMapper();
        GeminiResponse generativeResponse= objectMapper.readValue(response.body(), GeminiResponse.class);


        return generativeResponse;

    }


    public HttpRequest createEmbedContentRequest(EmbeddingRequest embeddingRequest) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(embeddingRequest);


        HttpRequest request = HttpRequest.newBuilder ()
                .uri(URI.create(BASE_URL+"/v1beta/models/"+MODEL_NAME+":embedContent?key="+API_KEY))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();



        return request;
    }

    public EmbeddingResponse handleEmbedContent(HttpResponse<String> httpResponse) throws JsonProcessingException {
        return objectMapper.readValue(httpResponse.body(), EmbeddingResponse.class);
    }


    public HttpRequest createBatchEmbedContentsRequest(BatchEmbeddingRequest batchEmbeddingRequest) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(batchEmbeddingRequest);


        HttpRequest request = HttpRequest.newBuilder ()
                .uri(URI.create(BASE_URL+"/v1beta/models/"+MODEL_NAME+":batchEmbedContents?key="+API_KEY))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();



        return request;
    }

    public BatchEmbeddingResponse handleEmbedContents(HttpResponse<String> httpResponse) throws JsonProcessingException {
        return objectMapper.readValue(httpResponse.body(), BatchEmbeddingResponse.class);
    }


}
