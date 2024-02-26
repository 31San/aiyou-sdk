package org.eu.miraikan.aiyou.model.gemini;


import org.eu.miraikan.aiyou.model.gemini.template.GeminiRequest;
import org.eu.miraikan.aiyou.model.gemini.template.GeminiResponse;
import org.eu.miraikan.aiyou.generativeClient.RestChatClient;
import org.eu.miraikan.aiyou.model.GenerativeModel;


import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * No multi turn conversation support
 */
public class GeminiProVision implements GenerativeModel {

    public static final String MODEL_NAME ="gemini-pro-vision";
    RestChatClient client;
    GeminiAdapter modelAdapter;

    public GeminiProVision(RestChatClient client) {
        this.client=client;
        modelAdapter = new GeminiAdapter(client.getClientConfig(),MODEL_NAME);
    }


    public GeminiResponse generateContent(GeminiRequest generativeRequest) throws IOException, InterruptedException {


        HttpRequest httpRequest = modelAdapter.createHttpRequest(generativeRequest);

        HttpResponse<String> httpResponse =client.generateContent(httpRequest);;



        return modelAdapter.handleHttpResponse(httpResponse);





    }
}
