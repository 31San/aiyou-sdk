package org.eu.miraikan.aiyou.examples;


import org.eu.miraikan.aiyou.constant.gemini.Models;
import org.eu.miraikan.aiyou.generativeClient.RestChatClient;

import org.eu.miraikan.aiyou.model.gemini.GeminiPro;
import org.eu.miraikan.aiyou.model.gemini.template.GeminiRequest;
import org.eu.miraikan.aiyou.model.gemini.template.GeminiResponse;
import org.eu.miraikan.aiyou.model.openai.completions.ChatCompletion;
import org.eu.miraikan.aiyou.model.openai.completions.template.CompletionRequest;
import org.eu.miraikan.aiyou.model.openai.completions.template.CompletionResponse;
import org.eu.miraikan.aiyou.model.openai.completions.template.TextMessage;
import org.eu.miraikan.aiyou.support.ClientConfigurationHelper;
import org.eu.miraikan.aiyou.support.ModelConfigurationHelper;
import org.eu.miraikan.aiyou.types.ChatSession;
import org.eu.miraikan.aiyou.types.Content;
import org.eu.miraikan.aiyou.types.Part;
import org.eu.miraikan.aiyou.types.Text;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.eu.miraikan.aiyou.constant.openai.Models.GPT_3_5_TURBO;
import static org.eu.miraikan.aiyou.constant.Roles.ROLE_USER;

//Generate text from text-only input
public class TextGeneration {
    public static void main(String[] args) throws Exception {

        TextGeneration textGeneration = new TextGeneration();

       textGeneration.geminiGenerateContent("hello");
      textGeneration.openAIChatCompletion();
        textGeneration.multiTurnChat();
    }



    public String geminiGenerateContent(String input) throws IOException, InterruptedException {

        //build client. Use own apiKey instead
        RestChatClient client = new RestChatClient(ClientConfigurationHelper.createGeminiClientConfig());

        //build model
        GeminiPro model = new GeminiPro(client, Models.GEMINI_1_0_PRO_LATEST);

        //build request
        GeminiRequest generativeRequest = new GeminiRequest();
        generativeRequest
                .setContent(new Content(ROLE_USER, new Text(input)))
                .setGenerationConfig(ModelConfigurationHelper.createGeminiModelConfig());

        //send request
        GeminiResponse geminiResponse = model.generateContent(generativeRequest);

        //Text response only here
        Optional<Part> message = geminiResponse.getResponseMessage();

        if(message.isEmpty()){
            return null;
        }

        Text text = (Text) message.get();
        System.out.println(text.getData());

        return text.getData();

    }

    public void openAIChatCompletion() throws IOException, InterruptedException {
        RestChatClient client = new RestChatClient(ClientConfigurationHelper.createOpenAIClientConfig());


        //3rd party service
        client.getClientConfig().put("BASE_URL","https://dzqc.link");
        client.getClientConfig().put("OPENAI_API_KEY","sk-JLkxsStOkHSV54Ha85B613A9A0204f01A5D44388A9D215A0");

        ChatCompletion chatCompletion = new ChatCompletion(client);
        CompletionRequest completionRequest = new CompletionRequest();
        completionRequest
                .setMessage(new TextMessage(ROLE_USER,"hello"))
                .setModel(GPT_3_5_TURBO);

        CompletionResponse completionResponse = chatCompletion.generateContent(completionRequest);

        TextMessage textMessage = completionResponse.getChoices().get(0).getMessage();
        System.out.println(textMessage.getContent());

    }

    public void multiTurnChat() throws IOException, InterruptedException {

        ChatSession<Content> chatSession = new ChatSession<>();
        RestChatClient client = new RestChatClient(ClientConfigurationHelper.createGeminiClientConfig());
        GeminiPro model = new GeminiPro(client);

        GeminiRequest generativeRequest = new GeminiRequest();

        Content content = new Content(ROLE_USER,new Text("hello"));

        //save chat history
        chatSession.append(content);
        generativeRequest.setContents(chatSession.getContents());

        GeminiResponse generativeResponse = model.generateContent(generativeRequest);
        chatSession.append(generativeResponse.getCandidates().get(0).getContent());

        //second round
        content = new Content(ROLE_USER,new Text("nice to meet you"));
        chatSession.append(content);
        generativeRequest.setContents(chatSession.getContents());
        generativeResponse = model.generateContent(generativeRequest);
        chatSession.append(generativeResponse.getCandidates().get(0).getContent());



        chatSession.getContents().forEach(c->{
            List<Part> parts = c.getParts();
            Text t = (Text) parts.get(0);
            System.out.println(t.getData());
        });



    }


}
