package org.eu.miraikan.aiyou.model.worksai.stableDiffusionXL;

import org.eu.miraikan.aiyou.types.Blob;

import org.eu.miraikan.aiyou.generativeClient.RestChatClient;
import org.eu.miraikan.aiyou.model.GenerativeModel;
import org.eu.miraikan.aiyou.types.Text;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class StableDiffusionXL implements GenerativeModel {

    StableDiffusionXLAdapter modelAdapter;
    public static final String MODEL_NAME = "stabilityai/stable-diffusion-xl-base-1.0";
    RestChatClient client;
    public StableDiffusionXL(RestChatClient client) {

        modelAdapter=new StableDiffusionXLAdapter(client.getClientConfig(),MODEL_NAME);
        this.client=client;


    }


    public Blob generateContent(Text text) throws IOException, InterruptedException {

        HttpRequest httpRequest = modelAdapter.createHttpRequest(text);

        HttpResponse<byte[]>httpResponse =client.generateBinaryContent(httpRequest);




        return  modelAdapter.handleHttpResponse(httpResponse);
    }
}
