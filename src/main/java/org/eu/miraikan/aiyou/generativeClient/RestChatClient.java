package org.eu.miraikan.aiyou.generativeClient;



import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;


/**
* Binding with rest api.
* Reusable
*/
public class RestChatClient extends GenerativeClient {

    HttpClient client=HttpClient.newHttpClient();;

    public RestChatClient() {}

    public RestChatClient(Map<String,String> clientConfig) {
        this.clientConfig = clientConfig;

    }





    public HttpResponse<String> generateContent(HttpRequest httpRequest) throws IOException, InterruptedException {
            return client.send (httpRequest, HttpResponse.BodyHandlers.ofString ());
    }

    public HttpResponse<byte[]> generateBinaryContent(HttpRequest httpRequest) throws IOException, InterruptedException {
        return client.send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());
    }


    public HttpResponse<InputStream> generateStreamContent(HttpRequest httpRequest) throws IOException, InterruptedException {
        return client.send(httpRequest,  HttpResponse.BodyHandlers.ofInputStream());
    }


}
