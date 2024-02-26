package org.eu.miraikan.aiyou.examples;

import org.eu.miraikan.aiyou.model.openai.textToSpeech.template.TextToSpeechRequest;
import org.eu.miraikan.aiyou.generativeClient.RestChatClient;
import org.eu.miraikan.aiyou.model.openai.textToSpeech.TextToSpeech;
import org.eu.miraikan.aiyou.support.ClientConfigurationHelper;
import org.eu.miraikan.aiyou.types.Blob;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import java.util.Map;
import java.util.Scanner;

import static org.eu.miraikan.aiyou.constant.Models.TTS_1;
import static org.eu.miraikan.aiyou.constant.Voices.ALLOY;

//create directory before running
public class TextToSpeechExample {
    public static void main(String[] args) throws IOException, InterruptedException {

        InputStream is = TextToSpeechExample.class.getResourceAsStream("/sample.txt");

        StringBuilder sb = new StringBuilder();
        Scanner scanner = new Scanner(is);
        while (scanner.hasNextLine()){
            sb.append(scanner.nextLine());
        }
        String str = sb.toString();


        RestChatClient client = new RestChatClient(ClientConfigurationHelper.createOpenAIClientConfig());

        //override with 3rd service
        client.getClientConfig().put("OPENAI_API_KEY","sk-mUzqrl4kEpDnerux71CfD9Bc5058401988Cc4e54600aF260");
        client.getClientConfig().put("BASE_URL","https://api.chatgptid.net");



        TextToSpeech model = new TextToSpeech(client);

        //use default mp3 format
        TextToSpeechRequest textToSpeechRequest = TextToSpeechRequest.builder()
                .input(str)
                .model(TTS_1)
                .voice(ALLOY).build();



        Blob blob = model.generateContent(textToSpeechRequest);

        byte[] bytes =  blob.getData().getData();

        String filePath="./output/speech.mp3";
        FileOutputStream fos = new FileOutputStream(filePath);
        fos.write(bytes);




        //streaming

        textToSpeechRequest.setStream(true);

        Iterator<byte[]> iterator = model.generateBinaryStreamContent(textToSpeechRequest);

        String chunkPath="./output/";

        int count=0;
        while(iterator.hasNext()){
            byte[] data = iterator.next();
            fos = new FileOutputStream(chunkPath+"tts"+count+".mp3");
            fos.write(data);
            count++;
        }


    }
}
