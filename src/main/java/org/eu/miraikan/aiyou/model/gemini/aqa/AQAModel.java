package org.eu.miraikan.aiyou.model.gemini.aqa;

import org.eu.miraikan.aiyou.generativeClient.RestChatClient;
import org.eu.miraikan.aiyou.model.gemini.aqa.template.AQAAdapter;
import org.eu.miraikan.aiyou.model.gemini.aqa.template.GenerateAnswerRequest;
import org.eu.miraikan.aiyou.model.gemini.aqa.template.GenerateAnswerResponse;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AQAModel {

    public static final String MODEL_NAME = "models/aqa";
    AQAAdapter aqaAdapter;
    RestChatClient client;
    public AQAModel(RestChatClient client){
        this.aqaAdapter = new AQAAdapter(client.getClientConfig(),MODEL_NAME);
        this.client = client;
    }
    public AQAModel(RestChatClient client, String modelName) {
        this.aqaAdapter = new AQAAdapter(client.getClientConfig(), modelName);
        this.client = client;
    }

    public GenerateAnswerResponse generateAnswer(GenerateAnswerRequest generateAnswerRequest) throws IOException, InterruptedException {
        HttpRequest httpRequest = aqaAdapter.createGenerateAnswerRequest(generateAnswerRequest);
        HttpResponse<String> httpResponse = client.generateContent(httpRequest);
        GenerateAnswerResponse generateAnswerResponse = aqaAdapter.handleGenerateAnswerResponse(httpResponse);
        return generateAnswerResponse;

    }
}
