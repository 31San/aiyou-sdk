package org.eu.miraikan.aiyou.model.gemini;



import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eu.miraikan.aiyou.model.gemini.template.GeminiRequest;
import org.eu.miraikan.aiyou.model.gemini.template.GeminiResponse;

import org.eu.miraikan.aiyou.model.ModelAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GeminiAdapter implements ModelAdapter<GeminiResponse> {
    String API_KEY;

    String BASE_URL ="https://generativelanguage.googleapis.com";

    String MODEL_NAME;

    ObjectMapper objectMapper;

    public GeminiAdapter(Map<String, String> config,String modelName) {
        API_KEY = config.get("API_KEY");
        BASE_URL = config.get("BASE_URL")==null?BASE_URL:config.get("BASE_URL");
        MODEL_NAME=modelName;
        objectMapper = new ObjectMapper();
    }



   public HttpRequest createHttpRequest(GeminiRequest generativeRequest) throws Exception {







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
