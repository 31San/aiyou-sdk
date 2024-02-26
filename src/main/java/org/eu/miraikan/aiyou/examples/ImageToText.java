package org.eu.miraikan.aiyou.examples;



import org.eu.miraikan.aiyou.model.gemini.template.GeminiRequest;
import org.eu.miraikan.aiyou.model.gemini.template.GeminiResponse;
import org.eu.miraikan.aiyou.generativeClient.RestChatClient;
import org.eu.miraikan.aiyou.model.gemini.GeminiProVision;
import org.eu.miraikan.aiyou.model.openai.completions.ChatCompletion;
import org.eu.miraikan.aiyou.model.openai.completions.template.*;
import org.eu.miraikan.aiyou.support.ClientConfigurationHelper;
import org.eu.miraikan.aiyou.types.Blob;
import org.eu.miraikan.aiyou.types.Content;
import org.eu.miraikan.aiyou.types.Part;
import org.eu.miraikan.aiyou.types.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import static org.eu.miraikan.aiyou.constant.Models.GPT_4_VISION_PREVIEW;
import static org.eu.miraikan.aiyou.constant.Roles.ROLE_USER;

public class ImageToText {
    public static void main(String[] args) throws IOException, InterruptedException {

        ImageToText imageToText = new ImageToText();
       imageToText.geminiGenerateContent();
    //   imageToText.openAIChatCompletion();


    }

    public String geminiGenerateContent() throws IOException, InterruptedException {

        //load image and convert to base64
        URL url = new URL("https://storage.googleapis.com/generativeai-downloads/images/scones.jpg");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        InputStream inputStream = connection.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int bytesRead;
        byte[] buffer = new byte[1024];
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        byte[] base64Image = outputStream.toByteArray();


        RestChatClient client = new RestChatClient(ClientConfigurationHelper.createGeminiClientConfig());
        GeminiProVision model = new GeminiProVision(client);

        GeminiRequest generativeRequest = new GeminiRequest();

        Content content = new Content(ROLE_USER,List.of(new Text("What is this picture?"),
                new Blob("image/jpeg", base64Image)));

        generativeRequest.setContent(content);

        GeminiResponse generativeResponse = model.generateContent(generativeRequest);

        Optional<Part> message =  generativeResponse.getResponseMessage();

        if(message.isEmpty()){
            return null;
        }

        Text text = (Text) message.get();



        System.out.println(text.getData());

        return text.getData();
    }


    //gpt4v is not tested yet
    public void openAIChatCompletion() throws IOException, InterruptedException {

        String url = "https://storage.googleapis.com/generativeai-downloads/images/scones.jpg";

        RestChatClient client = new RestChatClient();
        client.setClientConfig(ClientConfigurationHelper.createOpenAIClientConfig());

        //3rd party service
        client.getClientConfig().put("BASE_URL","https://dzqc.link");
        client.getClientConfig().put("OPENAI_API_KEY","sk-JLkxsStOkHSV54Ha85B613A9A0204f01A5D44388A9D215A0");

        ChatCompletion chatCompletion = new ChatCompletion(client);
        CompletionRequest completionRequest = new CompletionRequest();
        completionRequest.setModel(GPT_4_VISION_PREVIEW);
        VisionMessage visionMessage = new VisionMessage();
        MessageContent textContent = new MessageContent();
        textContent.setTypeText();
        textContent.setText("What is this picture?");
        MessageContent imageContent = new MessageContent();
        imageContent.setTypeImage();
        imageContent.setImage_url(url);
        visionMessage.setRole(ROLE_USER);
        visionMessage.setContent(List.of(textContent,imageContent));

        CompletionResponse completionResponse = chatCompletion.generateContent(completionRequest);

    }
}
