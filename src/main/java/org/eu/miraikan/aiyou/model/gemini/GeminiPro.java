package org.eu.miraikan.aiyou.model.gemini;


import org.eu.miraikan.aiyou.model.gemini.template.GeminiRequest;
import org.eu.miraikan.aiyou.model.gemini.template.GeminiResponse;
import org.eu.miraikan.aiyou.generativeClient.RestChatClient;
import org.eu.miraikan.aiyou.generativeClient.stream.StreamIterator;
import org.eu.miraikan.aiyou.model.GenerativeModel;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Iterator;




public class GeminiPro implements GenerativeModel {

    /**
     * hard coding
     */
    public static final String MODEL_NAME = "gemini-pro";
    RestChatClient client;
    GeminiAdapter modelAdapter;

    /**
     * use default model
     */

    public GeminiPro(RestChatClient client) {
        this.client=client;
        modelAdapter = new GeminiAdapter(client.getClientConfig(),MODEL_NAME);
    }

    /**
     * switch gemini pro model
     * @param client
     * @param MODEL_NAME
     */
    public GeminiPro(RestChatClient client,String MODEL_NAME) {
        this.client=client;
        modelAdapter = new GeminiAdapter(client.getClientConfig(),MODEL_NAME);
    }

    public GeminiResponse generateContent(GeminiRequest generativeRequest) throws IOException, InterruptedException {

        HttpRequest httpRequest = modelAdapter.createHttpRequest(generativeRequest);

        HttpResponse<String> httpResponse =client.generateContent(httpRequest);

        return modelAdapter.handleHttpResponse(httpResponse);

    };


    /**
     * gemini streaming doesn't follow web-sent-event
     * @param generativeRequest
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public Iterator<GeminiResponse> generateStreamContent(GeminiRequest generativeRequest) throws IOException, InterruptedException {


        HttpRequest httpRequest = modelAdapter.createStreamRequest(generativeRequest);

        HttpResponse<InputStream> httpResponse =client.generateStreamContent(httpRequest);

        return new StreamIterator<>(httpResponse.body(),modelAdapter);

    }
}
