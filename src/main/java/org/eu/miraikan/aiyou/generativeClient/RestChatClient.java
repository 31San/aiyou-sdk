package org.eu.miraikan.aiyou.generativeClient;



import java.io.InputStream;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;


//binding with rest api
//reused among one platform
public class RestChatClient extends GenerativeClient {

    HttpClient client;

    public RestChatClient() {
        client = HttpClient.newHttpClient();
    }

    public RestChatClient(Map<String,String> clientConfig) {
        this.clientConfig = clientConfig;

    }











    public HttpResponse<String> generateContent(HttpRequest httpRequest) throws Exception {



            return client.send (httpRequest, HttpResponse.BodyHandlers.ofString ());

    }

    public HttpResponse<byte[]> generateBinaryContent(HttpRequest httpRequest) throws Exception{
        return client.send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());
    }


    public HttpResponse<Stream<String>> generateStreamContent(HttpRequest httpRequest) throws Exception{


        return client.send(httpRequest, HttpResponse.BodyHandlers.ofLines());


    }


    public HttpResponse<InputStream> generateBinaryStreamContent(HttpRequest httpRequest) throws Exception{



        return client.send(httpRequest, HttpResponse.BodyHandlers.ofInputStream());

    }


}
