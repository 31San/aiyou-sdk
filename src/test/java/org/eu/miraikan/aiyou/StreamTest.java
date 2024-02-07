package org.eu.miraikan.aiyou;

import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//test for raw stream
public class StreamTest {
    @Test
    public void test() throws IOException, InterruptedException, URISyntaxException {

        String id="e220944b0adf25a44ed786389787fcbe";
        String apiToken="mxwfHIdpSuPNQr1Zhi3crsUvFQaJh-igWEFeU1_U";
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.cloudflare.com/client/v4/accounts/"+id+"/ai/run/@cf/meta/llama-2-7b-chat-int8"))
                .POST(HttpRequest.BodyPublishers.
                        ofString("{ \"prompt\": \"where is new york?please reply as long as possible.\", \"stream\": true }", StandardCharsets.UTF_8))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+apiToken)
                .build();



        var lines = client.send(request, HttpResponse.BodyHandlers.ofLines()).body();
       // lines.forEach(System.out::println);

        lines.forEach((input)->{
            Pattern pattern = Pattern.compile("\"response\":\"(.*)\"");
            Matcher matcher = pattern.matcher(input);

            while (matcher.find()) {
                System.out.print(matcher.group(1));
            }
        });

    }

 //   @Test
    public void test2() throws IOException, InterruptedException {
        Map<String,String> config = new HashMap<>();
        config.put("apiKey","AIzaSyAbZC0KdOyk4avCj-afLzX7x6rxbVnt59A");

        config.put("modelName","gemini-pro");
        String apiKey = config.get("apiKey");

        String modelName = config.get("modelName");
        HttpRequest request = HttpRequest.newBuilder ()
                .uri(URI.create("https://generativelanguage.googleapis.com/v1beta/models/"+modelName+":streamGenerateContent?key="+apiKey))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{ \"contents\":[{\"parts\":[{\"text\": \"Write long a story about a magic backpack.\"}]}]}"))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        var lines = client.send(request, HttpResponse.BodyHandlers.ofLines()).body();
         lines.forEach(s -> {
             System.out.println(s);
         });


    }
}
