package org.eu.miraikan.aiyou.model.openai.completions;


import org.eu.miraikan.aiyou.generativeClient.RestChatClient;
import org.eu.miraikan.aiyou.generativeClient.stream.StreamIterator;
import org.eu.miraikan.aiyou.model.GenerativeModel;
import org.eu.miraikan.aiyou.model.openai.completions.template.CompletionRequest;
import org.eu.miraikan.aiyou.model.openai.completions.template.CompletionResponse;

import java.io.InputStream;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Iterator;
import java.util.stream.Stream;

public class ChatCompletion implements GenerativeModel {
    RestChatClient client;
    CompletionAdapter adapter;

    public ChatCompletion(RestChatClient client) {
        this.client = client;
        this.adapter = new CompletionAdapter(client.getClientConfig());
    }

    public CompletionResponse generateContent(CompletionRequest completionRequest) throws Exception {
        HttpRequest httpRequest = adapter.createHttpRequest(completionRequest);

        HttpResponse<String> httpResponse =client.generateContent(httpRequest);


        return adapter.handleHttpResponse(httpResponse);

    }




    public Iterator<CompletionResponse> generateStreamContent(CompletionRequest completionRequest) throws Exception {
        HttpRequest httpRequest = adapter.createHttpRequest(completionRequest);

        HttpResponse<InputStream> httpResponse =client.generateStreamContent(httpRequest);

        StreamIterator<CompletionResponse> streamIterator = new StreamIterator<>(httpResponse.body(),adapter);

        return streamIterator;



    }
}
