package org.eu.miraikan.aiyou.model.gemini;

import org.eu.miraikan.aiyou.model.gemini.template.GeminiRequest;
import org.eu.miraikan.aiyou.model.gemini.template.GeminiResponse;
import org.eu.miraikan.aiyou.generativeClient.RestChatClient;
import org.eu.miraikan.aiyou.generativeClient.stream.StreamIterator;
import org.eu.miraikan.aiyou.model.GenerativeModel;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Iterator;
import java.util.stream.Stream;


//how to deal with history content, as well as safety and config context?
public class GeminiPro implements GenerativeModel {

    //hard coding
    public static final String MODEL_NAME = "gemini-pro";
    RestChatClient client;
    GeminiAdapter modelAdapter;

    //use default model
    public GeminiPro(RestChatClient client) {
        this.client=client;
        modelAdapter = new GeminiAdapter(client.getClientConfig(),MODEL_NAME);
    }

    //switch to gemini pro 1.5
    public GeminiPro(RestChatClient client,String MODEL_NAME) {
        this.client=client;
        modelAdapter = new GeminiAdapter(client.getClientConfig(),MODEL_NAME);
    }

    public GeminiResponse generateContent(GeminiRequest generativeRequest) throws Exception {

        //txt to part
        //part to content
        //content to generativeRequest;
        //adapter convert content to request
        //client send request

//        Part part=new Text(txt);
//        Content content=new Content(Content.RoleUser, new Part[]{part});
//        GenerativeRequest generativeRequest = new GenerativeRequest();
//        generativeRequest.setContents(new Content[] {content});


        HttpRequest httpRequest = modelAdapter.createHttpRequest(generativeRequest);

        HttpResponse<String> httpResponse =client.generateContent(httpRequest);

        System.out.println(httpResponse.body());

        return modelAdapter.handleHttpResponse(httpResponse);
        //response to content, to output
        //use adapter to convert response to Generative response

  //      return null;
    };


    //gemini not follow web-sent-event
    public Iterator<String> generateStreamContent(GeminiRequest generativeRequest) throws Exception {

//        Part part=new Text(txt);
//        Content content=new Content(Content.RoleUser, new Part[]{part});
//        GenerativeRequest generativeRequest = new GenerativeRequest();
//        generativeRequest.setContents(new Content[] {content});

        HttpRequest httpRequest = modelAdapter.createStreamRequest(generativeRequest);

        HttpResponse<Stream<String>> httpResponse =client.generateStreamContent(httpRequest);

        return new StreamIterator(httpResponse.body().iterator(),modelAdapter);

    }
}
