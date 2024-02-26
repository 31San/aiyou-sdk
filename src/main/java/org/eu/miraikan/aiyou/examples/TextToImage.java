package org.eu.miraikan.aiyou.examples;


import org.eu.miraikan.aiyou.generativeClient.RestChatClient;
import org.eu.miraikan.aiyou.model.worksai.stableDiffusionXL.StableDiffusionXL;
import org.eu.miraikan.aiyou.support.ClientConfigurationHelper;
import org.eu.miraikan.aiyou.types.Blob;
import org.eu.miraikan.aiyou.types.Text;

import java.io.FileOutputStream;
import java.io.IOException;


public class TextToImage {
    public static void main(String[] args) throws IOException, InterruptedException {
        TextToImage text = new TextToImage();
        text.stableDiffusion("Write long a story about a magic backpack.");


    }

    /**
     * Simply accept prompt and return bytes
     * @param prompt
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public  byte[] stableDiffusion(String prompt) throws IOException, InterruptedException {
        RestChatClient client = new RestChatClient(ClientConfigurationHelper.createWorksAIClientConfig());
        StableDiffusionXL model = new StableDiffusionXL(client);


        Text text = new Text(prompt);


        Blob blob = model.generateContent(text);


        byte[] bytes =  blob.getData().getData();
        String filePath="./output/image.png";
        FileOutputStream fos = new FileOutputStream(filePath);
        fos.write(bytes);
        return bytes;
    }
}
