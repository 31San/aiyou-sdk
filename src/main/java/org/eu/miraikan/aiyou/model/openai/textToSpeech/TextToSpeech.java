package org.eu.miraikan.aiyou.model.openai.textToSpeech;


import org.eu.miraikan.aiyou.generativeClient.stream.StreamIterator;
import org.eu.miraikan.aiyou.model.openai.textToSpeech.template.TextToSpeechRequest;
import org.eu.miraikan.aiyou.types.Blob;
import org.eu.miraikan.aiyou.generativeClient.RestChatClient;
import org.eu.miraikan.aiyou.model.GenerativeModel;


import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Iterator;

/**
 * speech  model
 */
public class TextToSpeech implements GenerativeModel {
    RestChatClient client;
    public static final String MODEL_NAME = "tts-1";
    TextToSpeechAdapter modelAdapter;
    public TextToSpeech(RestChatClient client) {
        this.client = client;
        client.getClientConfig().put("MODEL_NAME",MODEL_NAME);
        modelAdapter = new TextToSpeechAdapter(client.getClientConfig());
    }


    public Blob generateContent(TextToSpeechRequest textToSpeechRequest) throws IOException, InterruptedException {
        HttpRequest httpRequest = modelAdapter.createHttpRequest(textToSpeechRequest);

        HttpResponse<byte[]> httpResponse = client.generateBinaryContent(httpRequest);

        return  modelAdapter.handleHttpResponse(httpResponse);
    }



    public Iterator<byte[]> generateBinaryStreamContent(TextToSpeechRequest textToSpeechRequest) throws IOException, InterruptedException {

        HttpRequest httpRequest = modelAdapter.createHttpRequest(textToSpeechRequest);

        HttpResponse<InputStream> httpResponse = client.generateStreamContent(httpRequest);

        return  new StreamIterator(httpResponse.body(),modelAdapter);


    };
}
