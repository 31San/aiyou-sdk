package org.eu.miraikan.aiyou.examples;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.eu.miraikan.aiyou.generativeClient.RestChatClient;
import org.eu.miraikan.aiyou.model.gemini.GeminiPro;
import org.eu.miraikan.aiyou.model.gemini.template.GeminiRequest;
import org.eu.miraikan.aiyou.model.gemini.template.GeminiResponse;
import org.eu.miraikan.aiyou.support.ClientConfigurationHelper;
import org.eu.miraikan.aiyou.support.FunctionCallingHelper;
import org.eu.miraikan.aiyou.types.ChatSession;
import org.eu.miraikan.aiyou.types.Content;
import org.eu.miraikan.aiyou.types.Part;
import org.eu.miraikan.aiyou.types.Text;
import org.eu.miraikan.aiyou.types.functionCalling.*;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.eu.miraikan.aiyou.constant.Roles.*;

public class FunctionCallingExample {
    public static void main(String[] args) throws Exception {
        FunctionCallingExample functionCallingExample = new FunctionCallingExample();
    //    functionCallingExample.geminiSingleTurn();

        functionCallingExample.geminiMultiTurn();
    }


    //directly call api without function calling helper.
    public void geminiSingleTurn() throws Exception {

        RestChatClient client = new RestChatClient();
        client.setClientConfig(ClientConfigurationHelper.createGeminiClientConfig());
        GeminiPro model = new GeminiPro(client);


        GeminiRequest geminiRequest = new GeminiRequest();
        geminiRequest.setContents(List.of(new Content(ROLE_USER,List.of(new Text("Which theaters in Mountain View show Barbie movie?")))));
        Tool tool = new Tool();
        List<FunctionDeclaration> functionDeclarations = new ArrayList<>();

        FunctionDeclaration functionDeclaration = new FunctionDeclaration();

        functionDeclaration.setName("find_theaters");
        functionDeclaration.setDescription("find movie titles currently playing in theaters based on any description, genre, title words, etc.");




        functionDeclaration.setParameters(MovieAndTheater.class);

        functionDeclarations.add(functionDeclaration);
        tool.setFunctionDeclarations(functionDeclarations);

        geminiRequest.setTools(List.of(tool));

        GeminiResponse geminiResponse = model.generateContent(geminiRequest);


        Part part =  geminiResponse.getCandidates().get(0)
                .getContent().getParts().get(0);

        FunctionCall functionCall = part instanceof FunctionCall ? ((FunctionCall) part) : null;

        if(functionCall != null){
            System.out.println(functionCall.getName());

            System.out.println(functionCall.getArgs());
        }

    }



    public void geminiMultiTurn() throws Exception {

        String fakeResponse = "{\n" +
                "      \"functionResponse\": {\n" +
                "        \"name\": \"find_theaters\",\n" +
                "        \"response\": {\n" +
                "          \"name\": \"find_theaters\",\n" +
                "          \"content\": {\n" +
                "            \"movie\": \"Barbie\",\n" +
                "            \"theaters\": [{\n" +
                "              \"name\": \"AMC Mountain View 16\",\n" +
                "              \"address\": \"2000 W El Camino Real, Mountain View, CA 94040\"\n" +
                "            }, {\n" +
                "              \"name\": \"Regal Edwards 14\",\n" +
                "              \"address\": \"245 Castro St, Mountain View, CA 94040\"\n" +
                "            }]\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }";

        System.out.println(fakeResponse);
        FunctionCallingHelper functionCallingHelper = new FunctionCallingHelper();


        //lambada should be implement Function interface
        FunctionDeclaration functionDeclaration = functionCallingHelper
                .addFunction("find_theaters",MovieAndTheater.class, (MovieAndTheater movieAndTheater) -> fakeResponse)
                .setDescription("find movie titles currently playing in theaters based on any description, genre, title words, etc.");

        List<FunctionDeclaration> functionDeclarations = functionCallingHelper.getFunctionDeclarations();

        //build request
        RestChatClient client = new RestChatClient();
        client.setClientConfig(ClientConfigurationHelper.createGeminiClientConfig());
        GeminiPro model = new GeminiPro(client);

        ChatSession<Content> chatSession = new ChatSession<>();
        chatSession.append(new Content(ROLE_USER,List.of(new Text("Which theaters in Mountain View show Barbie movie?"))));

        GeminiRequest geminiRequest = new GeminiRequest();
        geminiRequest.setContents(chatSession.getContents());
        Tool tool = new Tool();
        tool.setFunctionDeclarations(functionDeclarations);
        geminiRequest.setTools(List.of(tool));

        GeminiResponse geminiResponse = model.generateContent(geminiRequest);


        Part part =  geminiResponse.getCandidates().get(0)
                .getContent().getParts().get(0);

        FunctionCall functionCall = part instanceof FunctionCall ? ((FunctionCall) part) : null;

        if(functionCall==null){
            return;
        }

        //synchronized functionCalling
        Object message = functionCallingHelper.callFunction(functionCall.getName(), functionCall.getArgs());
        ObjectMapper objectMapper = new ObjectMapper();
        String json1 = objectMapper.writeValueAsString(functionCall);
        String json2 = objectMapper.writeValueAsString(message);


        chatSession.append(new Content(ROLE_MODEL,List.of(new Text(json1))));

        chatSession.append(new Content(ROLE_USER,List.of(new Text(json2))));

         geminiRequest.setContents(chatSession.getContents());

        geminiResponse = model.generateContent(geminiRequest);

        Text text = (Text) geminiResponse.getCandidates().get(0)
                .getContent().getParts().get(0);

        System.out.println(text.getData());

        //async
        CompletableFuture<Object> message1 = functionCallingHelper.executeAsync(functionCall.getName(), functionCall.getArgs());
        message1.thenAccept(
                System.out::println
        );

    }



    //json model with annotation
    public static  class MovieAndTheater{
        @JsonPropertyDescription("Any movie title")
        public String movie;
        @JsonPropertyDescription("The city and state, e.g. San Francisco, CA or a zip code e.g. 95616")
        @JsonProperty(required = true)
        public String location;
    }


}
