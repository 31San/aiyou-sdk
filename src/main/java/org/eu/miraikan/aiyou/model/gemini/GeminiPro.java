package org.eu.miraikan.aiyou.model.gemini;

import org.eu.miraikan.aiyou.model.gemini.template.GeminiRequest;
import org.eu.miraikan.aiyou.model.gemini.template.GeminiResponse;
import org.eu.miraikan.aiyou.generativeClient.RestChatClient;
import org.eu.miraikan.aiyou.generativeClient.stream.StreamIterator;
import org.eu.miraikan.aiyou.model.GenerativeModel;

import java.io.InputStream;
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
    public GeminiPro(RestChatClient client) {
        this.client=client;
        modelAdapter = new GeminiAdapter(client.getClientConfig(),MODEL_NAME);
    }



    public GeminiResponse generateContent(GeminiRequest generativeRequest) throws Exception {




        HttpRequest httpRequest = modelAdapter.createHttpRequest(generativeRequest);

        HttpResponse<String> httpResponse =client.generateContent(httpRequest);

        System.out.println(httpResponse.body());

        return modelAdapter.handleHttpResponse(httpResponse);

    };


    //gemini not follow web-sent-event
    public Iterator<GeminiResponse> generateStreamContent(GeminiRequest generativeRequest) throws Exception {



        HttpRequest httpRequest = modelAdapter.createStreamRequest(generativeRequest);

        HttpResponse<InputStream> httpResponse =client.generateStreamContent(httpRequest);

        return new StreamIterator<>(httpResponse.body(),modelAdapter);

    }
}
