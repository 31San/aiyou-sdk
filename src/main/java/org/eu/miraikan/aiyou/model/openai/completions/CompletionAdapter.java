package org.eu.miraikan.aiyou.model.openai.completions;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eu.miraikan.aiyou.model.openai.OpenAIAdapter;
import org.eu.miraikan.aiyou.model.openai.completions.template.Choice;
import org.eu.miraikan.aiyou.model.openai.completions.template.CompletionRequest;
import org.eu.miraikan.aiyou.model.openai.completions.template.CompletionResponse;
import org.eu.miraikan.aiyou.model.openai.completions.template.TextMessage;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Iterator;
import java.util.Map;

public class CompletionAdapter extends OpenAIAdapter<CompletionResponse> {

    ObjectMapper objectMapper;

    public CompletionAdapter(Map<String, String> config) {
        OPENAI_API_KEY = config.get("OPENAI_API_KEY");
        BASE_URL = config.get("BASE_URL")==null?BASE_URL:config.get("BASE_URL");
        objectMapper = new ObjectMapper();

    }

    public HttpRequest createHttpRequest(CompletionRequest completionRequest) throws JsonProcessingException {



        String json = objectMapper.writeValueAsString(completionRequest);


        HttpRequest request = HttpRequest.newBuilder ()
                .uri(URI.create(BASE_URL+"/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization"," Bearer "+OPENAI_API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();



        return request;

    }


    public CompletionResponse handleHttpResponse(HttpResponse<String> httpResponse) throws JsonProcessingException {


        return objectMapper.readValue(httpResponse.body(),CompletionResponse.class);


    }

    @Override
    public CompletionResponse handleStream(Iterator<String> iterator){



        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.addMixIn(Choice.class,ChoiceMixin.class);
        String line = iterator.next();

        if(line.length()>5){
            line = line.substring(5);
        }
        if(line.trim().equals("[DONE]")){
            return null;
        }

        //skip empty line
        if (iterator.hasNext()){
            iterator.next();
        }

        try {
           return objectMapper.readValue(line,CompletionResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
       return null;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public abstract class ChoiceMixin {
        @JsonAlias("delta")
        abstract void setMessage(TextMessage message);
    }

}
