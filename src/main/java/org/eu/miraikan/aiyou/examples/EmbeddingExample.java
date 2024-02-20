package org.eu.miraikan.aiyou.examples;

import org.eu.miraikan.aiyou.constant.Models;
import org.eu.miraikan.aiyou.generativeClient.RestChatClient;
import org.eu.miraikan.aiyou.model.gemini.EmbeddingModel;
import org.eu.miraikan.aiyou.model.gemini.GeminiPro;
import org.eu.miraikan.aiyou.model.gemini.template.*;
import org.eu.miraikan.aiyou.support.ClientConfigurationHelper;
import org.eu.miraikan.aiyou.support.ModelConfigurationHelper;
import org.eu.miraikan.aiyou.types.Content;
import org.eu.miraikan.aiyou.types.Embedding;
import org.eu.miraikan.aiyou.types.Text;

import java.util.List;

import static org.eu.miraikan.aiyou.constant.Roles.ROLE_USER;

public class EmbeddingExample {
    public static void main(String[] args){
        EmbeddingExample embeddingExample = new EmbeddingExample();
        try {
            embeddingExample.geminiEmbedContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void geminiEmbedContent() throws Exception {
        RestChatClient client = new RestChatClient();
        client.setClientConfig(ClientConfigurationHelper.createGeminiClientConfig());
        EmbeddingModel model = new EmbeddingModel(client);


        Text text = new Text("Hello");

        EmbeddingRequest embeddingRequest = new EmbeddingRequest();
        embeddingRequest.setModel(Models.EMBEDDING_001);
        embeddingRequest.setContent(new Content(null,List.of(text)));


        EmbeddingResponse embeddingResponse = model.embedContent(embeddingRequest);

        List<Float> list = embeddingResponse.getEmbedding().getValues();

        list.forEach(System.out::println);

        System.out.println("\n\n");



        // batchEmbedding

        Text text1 = new Text("How are you");

        EmbeddingRequest embeddingRequest1 = new EmbeddingRequest();
        embeddingRequest1.setModel(Models.EMBEDDING_001);
        embeddingRequest1.setContent(new Content(null,List.of(text)));

        BatchEmbeddingRequest batchEmbeddingRequest = new BatchEmbeddingRequest();
        batchEmbeddingRequest.setRequests(List.of(embeddingRequest,embeddingRequest1));

        BatchEmbeddingResponse batchEmbeddingResponse = model.batchEmbedContents(batchEmbeddingRequest);

        List<Embedding> embeddings = batchEmbeddingResponse.getEmbeddings();

        embeddings.forEach(
                e->{

                    List<Float> list1 = embeddingResponse.getEmbedding().getValues();

                    list1.forEach(System.out::println);

                    System.out.println("\n\n");
                }
        );

    }


}
