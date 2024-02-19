package org.eu.miraikan.aiyou.examples;


import org.eu.miraikan.aiyou.model.gemini.template.GeminiRequest;
import org.eu.miraikan.aiyou.generativeClient.RestChatClient;
import org.eu.miraikan.aiyou.model.gemini.GeminiPro;
import org.eu.miraikan.aiyou.model.gemini.template.GeminiResponse;
import org.eu.miraikan.aiyou.model.openai.completions.ChatCompletion;
import org.eu.miraikan.aiyou.model.openai.completions.template.CompletionRequest;
import org.eu.miraikan.aiyou.model.openai.completions.template.CompletionResponse;
import org.eu.miraikan.aiyou.model.openai.completions.template.TextMessage;
import org.eu.miraikan.aiyou.support.ClientConfigurationHelper;
import org.eu.miraikan.aiyou.support.ModelConfigurationHelper;
import org.eu.miraikan.aiyou.types.Content;
import org.eu.miraikan.aiyou.types.Text;

import java.util.Iterator;
import java.util.List;

import static org.eu.miraikan.aiyou.constant.Models.GPT_3_5_TURBO;
import static org.eu.miraikan.aiyou.constant.Roles.ROLE_USER;

public class TextStreaming {
    public static void main(String[] args) throws Exception {

        TextStreaming textStreaming = new TextStreaming();
        textStreaming.openAIGenerateStreamContent();

    //    textStreaming.geminiGenerateStreamContent();

    }

    public void geminiGenerateStreamContent() throws Exception {
        RestChatClient client = new RestChatClient();
        client.setClientConfig(ClientConfigurationHelper.createGeminiClientConfig());
        GeminiPro model = new GeminiPro(client);

        GeminiRequest generativeRequest = new GeminiRequest();
        Text text = new Text("Write long a story about a magic backpack.");
        generativeRequest.setContents(List.of(new Content(
                ROLE_USER,List.of(text)
        )));


        generativeRequest.setGenerationConfig(ModelConfigurationHelper.createGeminiModelConfig());



        Iterator<GeminiResponse> iterator = model.generateStreamContent(generativeRequest);


        while (iterator.hasNext()){
            Text text1 = (Text) iterator.next().getCandidates().get(0).getContent().getParts().get(0);

            System.out.print(text1.getData());

        }
    }

    public void openAIGenerateStreamContent() throws Exception {
        RestChatClient client = new RestChatClient();
        client.setClientConfig(ClientConfigurationHelper.createOpenAIClientConfig());

        //3rd party service
        client.getClientConfig().put("BASE_URL","https://dzqc.link");
        client.getClientConfig().put("OPENAI_API_KEY","sk-JLkxsStOkHSV54Ha85B613A9A0204f01A5D44388A9D215A0");

        ChatCompletion chatCompletion = new ChatCompletion(client);
        CompletionRequest completionRequest = new CompletionRequest();
        completionRequest.setModel(GPT_3_5_TURBO);
        completionRequest.setStream(true);
        TextMessage textMessage = new TextMessage(ROLE_USER,"Write a short story about a magic backpack");
        completionRequest.setMessages(List.of(textMessage));

        Iterator<CompletionResponse> iterator = chatCompletion.generateStreamContent(completionRequest);


        while (iterator.hasNext()){
            CompletionResponse next = iterator.next();
            String str = next.getChoices().get(0).getMessage().getContent();
            //final one finish reason is stop and content will be null
            if(str!=null){
                System.out.print(str);

            }

        }


    }

}
