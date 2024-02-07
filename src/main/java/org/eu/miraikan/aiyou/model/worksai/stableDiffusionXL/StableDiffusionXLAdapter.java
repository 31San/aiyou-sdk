package org.eu.miraikan.aiyou.model.worksai.stableDiffusionXL;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.eu.miraikan.aiyou.model.worksai.WorkersAIAdapter;
import org.eu.miraikan.aiyou.types.Blob;

import org.eu.miraikan.aiyou.types.Text;


import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.Map;

public class StableDiffusionXLAdapter extends WorkersAIAdapter {



    String MODEL_NAME;

    ObjectMapper objectMapper;


    public StableDiffusionXLAdapter(Map<String,String> config,String modelName) {
        BASE_URL= config.get("BASE_URL")==null?BASE_URL:config.get("BASE_URL");
        API_TOKEN = config.get("API_TOKEN");
        MODEL_NAME = modelName;
        ACCOUNT_ID = config.get("ACCOUNT_ID");
        objectMapper=new ObjectMapper();
        objectMapper.addMixIn(Text.class,TextMixin.class);
    }

    public HttpRequest createHttpRequest(Text text) throws Exception {





        String json = objectMapper.writeValueAsString(text);








        HttpRequest request = HttpRequest.newBuilder ()
                .uri(URI.create(BASE_URL+"/client/v4/accounts/"+ACCOUNT_ID+"/ai/run/@cf/"+MODEL_NAME))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+API_TOKEN)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();



        return request;
    }


    public Blob handleHttpResponse(HttpResponse<byte[]> response) throws Exception{





        return new Blob("image/png",response.body());

    }

    public abstract class TextMixin{
        @JsonProperty("prompt")
        public abstract String getData();
    }
}
