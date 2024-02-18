package org.eu.miraikan.aiyou.model.openai.sora;

import org.eu.miraikan.aiyou.generativeClient.RestChatClient;
import org.eu.miraikan.aiyou.types.Blob;
import org.eu.miraikan.aiyou.types.Text;

//under develop
public class Sora {
    RestChatClient client;
    SoraAdapter adapter;

    public Sora(RestChatClient client) {
        this.client = client;
        this.adapter = new SoraAdapter(client.getClientConfig());
    }


    public Blob generateContent(Text text){
        return null;
    }

    public Blob generateImage(Text text){
        return null;
    }

    public Blob imageToVideo(Blob image){
        return null;
    }

    public Blob videoToVideo(Blob video){
        return null;
    }

    public Blob connectVideo(Blob video1,Blob video2){
        return null;
    }
}
