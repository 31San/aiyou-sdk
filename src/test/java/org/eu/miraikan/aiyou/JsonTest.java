package org.eu.miraikan.aiyou;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eu.miraikan.aiyou.model.gemini.template.GeminiRequest;
import org.eu.miraikan.aiyou.model.openai.completions.template.*;
import org.eu.miraikan.aiyou.types.Blob;
import org.eu.miraikan.aiyou.types.Content;
import org.eu.miraikan.aiyou.types.Part;
import org.eu.miraikan.aiyou.types.Text;
import org.eu.miraikan.aiyou.types.functionCalling.FunctionDeclaration;
import org.eu.miraikan.aiyou.types.functionCalling.Parameters;
import org.eu.miraikan.aiyou.types.functionCalling.Property;
import org.eu.miraikan.aiyou.types.functionCalling.Tool;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.eu.miraikan.aiyou.constant.Models.GPT_3_5_TURBO;

import static org.eu.miraikan.aiyou.constant.Roles.ROLE_USER;
import static org.junit.Assert.assertEquals;


//useful test for json formation
public class JsonTest {

   // @Test
    public void serializeRequestToJson() throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        String txt = "Hello, world!";

        Part part=new Text(txt);
        Content content=new Content(ROLE_USER, List.of(part));
        GeminiRequest generativeRequest = new GeminiRequest();
        generativeRequest.setContents(List.of(content));



        String jsonStr = mapper.writeValueAsString(generativeRequest);

        System.out.println(jsonStr);

        Blob blob = new Blob(txt,txt.getBytes());

        Content content2=new Content(ROLE_USER, List.of(part));
        GeminiRequest generativeRequest2 = new GeminiRequest();
        generativeRequest2.setContents(List.of(content));

        String jsonStr2 = mapper.writeValueAsString(generativeRequest2);

        System.out.println(jsonStr2);

    //    Request comparisonRequest = mapper.readValue(jsonStr, Request.class);


     //   assertEquals(request, comparisonRequest);
    }

 //   @Test
    public void completionTest() throws JsonProcessingException {
        CompletionRequest completionRequest = new CompletionRequest();

        Message message = new TextMessage("user","hello");



        completionRequest.setMessages(List.of(message));

        completionRequest.setModel(GPT_3_5_TURBO);

        ObjectMapper objectMapper = new ObjectMapper();



        String json = objectMapper.writeValueAsString(completionRequest);

        System.out.println(json);

        // message for gpt4 vision


        VisionMessage visionMessage = new VisionMessage();
        visionMessage.setRole("user");
        MessageContent messageContent = new MessageContent();
        messageContent.setText("hello");
        messageContent.setTypeText();
        visionMessage.setContent(List.of(messageContent));

        System.out.println(objectMapper.writeValueAsString(visionMessage));

    }

   // @Test
    public void prompt() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Text text = new Text("hello");
        String json = objectMapper.writeValueAsString(text);

        System.out.println(json);

    }

    @Test
    public void geminiFunctionCalling() throws JsonProcessingException {
        GeminiRequest geminiRequest = new GeminiRequest();
        geminiRequest.setContents(List.of(new Content(ROLE_USER,List.of(new Text("hello")))));
        Tool  tool = new Tool();
        List<FunctionDeclaration> functionDeclarations = new ArrayList<>();

        FunctionDeclaration functionDeclaration = new FunctionDeclaration();

        functionDeclaration.setName("test");
        functionDeclaration.setDescription("description");
        Parameters parameters = new Parameters();
        parameters.setType("Object");
        Property name = new Property();
        name.setType("String");
        name.setDescription("name description");
        parameters.setProperties(Map.of("name",name));
        functionDeclaration.setParameters(parameters);

        functionDeclarations.add(functionDeclaration);
        tool.setFunctionDeclarations(functionDeclarations);

        geminiRequest.setTools(List.of(tool));

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(geminiRequest);

        System.out.println(json);


    }
}