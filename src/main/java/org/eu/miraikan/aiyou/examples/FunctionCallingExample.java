package org.eu.miraikan.aiyou.examples;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eu.miraikan.aiyou.constant.Models;
import org.eu.miraikan.aiyou.generativeClient.RestChatClient;
import org.eu.miraikan.aiyou.model.gemini.GeminiPro;
import org.eu.miraikan.aiyou.model.gemini.template.GeminiRequest;
import org.eu.miraikan.aiyou.model.gemini.template.GeminiResponse;
import org.eu.miraikan.aiyou.support.ClientConfigurationHelper;
import org.eu.miraikan.aiyou.support.FunctionCallingHelper;
import org.eu.miraikan.aiyou.types.Content;
import org.eu.miraikan.aiyou.types.Part;
import org.eu.miraikan.aiyou.types.Text;
import org.eu.miraikan.aiyou.types.functionCalling.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.eu.miraikan.aiyou.constant.Roles.ROLE_USER;

public class FunctionCallingExample {
    public static void main(String[] args) throws Exception {
        FunctionCallingExample functionCallingExample = new FunctionCallingExample();
        functionCallingExample.geminiSingleTurn();
    }


    //directly call api
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
        Parameters parameters = new Parameters();
        parameters.setType("Object");

        Property location = new Property();
        location.setType("String");
        location.setDescription("The city and state, e.g. San Francisco, CA or a zip code e.g. 95616");

        Property movie = new Property();
        movie.setType("String");
        movie.setDescription("Any movie title");


        parameters.setProperties(Map.of("location",location,"movie",movie));

        parameters.setRequired(List.of("location"));

        functionDeclaration.setParameters(parameters);

        functionDeclarations.add(functionDeclaration);
        tool.setFunctionDeclarations(functionDeclarations);

        geminiRequest.setTools(List.of(tool));

        GeminiResponse geminiResponse = model.generateContent(geminiRequest);


        Part part = (FunctionCall) geminiResponse.getCandidates().get(0)
                .getContent().getParts().get(0);

        FunctionCall functionCall = part instanceof FunctionCall ? ((FunctionCall) part) : null;

        if(functionCall != null){
            System.out.println(functionCall.getName());

            System.out.println(functionCall.getArgs());
        }

    }

    public void functionCallHelper(){
        FunctionCallingHelper functionCallingHelper = new FunctionCallingHelper();
       // functionCallingHelper.register();
    }

    public static class MovieAndTheater{
        public String movie;
        public String theater;
    }

    public interface FindTheaters{
        void find_theaters(MovieAndTheater movieAndTheater);
    }

}
