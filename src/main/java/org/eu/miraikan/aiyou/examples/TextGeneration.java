package org.eu.miraikan.aiyou.examples;

import org.eu.miraikan.aiyou.constant.Models;
import org.eu.miraikan.aiyou.constant.Roles;
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

import java.util.List;

import static org.eu.miraikan.aiyou.constant.Models.GPT_3_5_TURBO;
import static org.eu.miraikan.aiyou.constant.Roles.ROLE_USER;

//Generate text from text-only input
public class TextGeneration {
    public static void main(String[] args) throws Exception {

        TextGeneration textGeneration = new TextGeneration();

       textGeneration.geminiGenerateContent("hello");
      textGeneration.openAIChatCompletion();
        textGeneration.multiTurnChat();
    }


    public String geminiGenerateContent(String input) throws Exception {
        RestChatClient client = new RestChatClient();
        client.setClientConfig(ClientConfigurationHelper.createGeminiClientConfig());
        GeminiPro model = new GeminiPro(client, Models.GEMINI_1_0_PRO_LATEST);

        GeminiRequest generativeRequest = new GeminiRequest();
        Text text = new Text(input);
        generativeRequest.setContents(List.of(new Content(
                ROLE_USER,List.of(text)
        )));


        generativeRequest.setGenerationConfig(ModelConfigurationHelper.createGeminiModelConfig());



        GeminiResponse generativeResponse = model.generateContent(generativeRequest);

        Text text1 = (Text) generativeResponse.getCandidates().get(0)
                .getContent().getParts().get(0);

        System.out.println(text1.getData());

        return text1.getData();
    }

    public void openAIChatCompletion() throws Exception{
        RestChatClient client = new RestChatClient();
        client.setClientConfig(ClientConfigurationHelper.createOpenAIClientConfig());

        //3rd party service
        client.getClientConfig().put("BASE_URL","https://dzqc.link");
        client.getClientConfig().put("OPENAI_API_KEY","sk-JLkxsStOkHSV54Ha85B613A9A0204f01A5D44388A9D215A0");

        ChatCompletion chatCompletion = new ChatCompletion(client);
        CompletionRequest completionRequest = new CompletionRequest();
        completionRequest.setModel(GPT_3_5_TURBO);

        TextMessage textMessage = new TextMessage(ROLE_USER,"hello");
        completionRequest.setMessages(List.of(textMessage));

         CompletionResponse completionResponse = chatCompletion.generateContent(completionRequest);



    }

    public void multiTurnChat() throws Exception {

        ChatSession<Content> chatSession = new ChatSession<>();
        RestChatClient client = new RestChatClient();
        client.setClientConfig(ClientConfigurationHelper.createGeminiClientConfig());
        GeminiPro model = new GeminiPro(client);

        GeminiRequest generativeRequest = new GeminiRequest();
        Text text = new Text("hello");
        Content content = new Content(
                ROLE_USER,List.of(text)
        );
        chatSession.append(content);
        generativeRequest.setContents(chatSession.getContents());
        GeminiResponse generativeResponse = model.generateContent(generativeRequest);
        chatSession.append(generativeResponse.getCandidates().get(0).getContent());

        //second round
        text = new Text("nice to meet you");
        content = new Content(ROLE_USER,List.of(text));
        chatSession.append(content);
        generativeRequest.setContents(chatSession.getContents());
        generativeResponse = model.generateContent(generativeRequest);
        chatSession.append(generativeResponse.getCandidates().get(0).getContent());

        List<Content> contents = chatSession.getContents();
        for (Content c : contents) {
            List<Part> parts = c.getParts();
            Text t = (Text) parts.get(0);
            System.out.println(t.getData());
        }




    }
}
