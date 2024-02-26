package org.eu.miraikan.aiyou.model.gemini;


import org.eu.miraikan.aiyou.generativeClient.RestChatClient;
import org.eu.miraikan.aiyou.model.gemini.template.BatchEmbeddingRequest;
import org.eu.miraikan.aiyou.model.gemini.template.BatchEmbeddingResponse;
import org.eu.miraikan.aiyou.model.gemini.template.EmbeddingRequest;
import org.eu.miraikan.aiyou.model.gemini.template.EmbeddingResponse;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class EmbeddingModel {

    //hard coding
    public static final String MODEL_NAME = "embedding-001";
    RestChatClient client;
    GeminiAdapter modelAdapter;

    public EmbeddingModel(RestChatClient client) {
        this.client = client;
        modelAdapter = new GeminiAdapter(client.getClientConfig(), MODEL_NAME);

    }



    public EmbeddingResponse embedContent(EmbeddingRequest embeddingRequest) throws IOException, InterruptedException {
        HttpRequest httpRequest = modelAdapter.createEmbedContentRequest(embeddingRequest);
        HttpResponse<String> httpResponse = client.generateContent(httpRequest);

        return modelAdapter.handleEmbedContent(httpResponse);
    }

    public BatchEmbeddingResponse batchEmbedContents(BatchEmbeddingRequest batchEmbeddingRequest) throws IOException, InterruptedException {
        HttpRequest httpRequest = modelAdapter.createBatchEmbedContentsRequest(batchEmbeddingRequest);
        HttpResponse<String> httpResponse = client.generateContent(httpRequest);

        return modelAdapter.handleEmbedContents(httpResponse);
    };
}
