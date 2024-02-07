package org.eu.miraikan.aiyou.model.gemini;



import com.fasterxml.jackson.databind.ObjectMapper;
import org.eu.miraikan.aiyou.model.gemini.template.GeminiRequest;
import org.eu.miraikan.aiyou.model.gemini.template.GeminiResponse;

import org.eu.miraikan.aiyou.model.ModelAdapter;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GeminiAdapter implements ModelAdapter<String> {
    String API_KEY;

    String BASE_URL ="https://generativelanguage.googleapis.com";

    String MODEL_NAME;

    public GeminiAdapter(Map<String, String> config,String modelName) {
        API_KEY = config.get("API_KEY");
        BASE_URL = config.get("BASE_URL")==null?BASE_URL:config.get("BASE_URL");
        MODEL_NAME=modelName;
    }



   public HttpRequest createHttpRequest(GeminiRequest generativeRequest) throws Exception {



        ObjectMapper objectMapper = new ObjectMapper();



        String json = objectMapper.writeValueAsString(generativeRequest);

        //test only
        System.out.println(json);


        //create request


        HttpRequest request = HttpRequest.newBuilder ()
                .uri(URI.create(BASE_URL+"/v1beta/models/"+MODEL_NAME+":generateContent?key="+API_KEY))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();



        return request;
    }


    public HttpRequest createStreamRequest( GeminiRequest generativeRequest) throws Exception{
        //deal with generativeRequest to json



        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(generativeRequest);





        HttpRequest request = HttpRequest.newBuilder ()
                .uri(URI.create(BASE_URL+"/v1beta/models/"+MODEL_NAME+":streamGenerateContent?key="+API_KEY))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();



        return request;
    }

    //roughly implementation, return generated text directly. Maybe Use Json Stream instead.
    @Override
    public  String handleStream(Iterator<String> iterator){

        Pattern pattern = Pattern.compile("\"text\":\\s*\"(.*)\"");

        while (iterator.hasNext()){
            String line = iterator.next();
       //    System.out.println(line);
            Matcher matcher = pattern.matcher(line);
            if(matcher.find()){
                return matcher.group(1);
            }
        }

        return null;
    }

    public GeminiResponse handleHttpResponse(HttpResponse httpResponse) throws Exception{
        HttpResponse<String> response = httpResponse;
        ObjectMapper objectMapper = new ObjectMapper();
        GeminiResponse generativeResponse= objectMapper.readValue(response.body(), GeminiResponse.class);


        return generativeResponse;

    }

    //for json view
    //may need to add json view and method in model class
  //  public interface Gemini{}
}
