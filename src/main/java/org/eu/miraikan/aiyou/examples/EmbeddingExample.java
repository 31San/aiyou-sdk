package org.eu.miraikan.aiyou.examples;


import org.eu.miraikan.aiyou.constant.gemini.Models;
import org.eu.miraikan.aiyou.generativeClient.RestChatClient;
import org.eu.miraikan.aiyou.model.gemini.EmbeddingModel;
import org.eu.miraikan.aiyou.model.gemini.template.*;
import org.eu.miraikan.aiyou.support.ClientConfigurationHelper;
import org.eu.miraikan.aiyou.types.Content;
import org.eu.miraikan.aiyou.types.Embedding;
import org.eu.miraikan.aiyou.types.Text;

import java.io.IOException;
import java.util.List;

public class EmbeddingExample {
    public static void main(String[] args) throws IOException, InterruptedException {
        EmbeddingExample embeddingExample = new EmbeddingExample();

            embeddingExample.geminiEmbedContent();

    }

    public void geminiEmbedContent() throws IOException, InterruptedException {
        RestChatClient client = new RestChatClient(ClientConfigurationHelper.createGeminiClientConfig());


        EmbeddingModel model = new EmbeddingModel(client);
        Text text = new Text("Hello");
        EmbeddingRequest embeddingRequest = EmbeddingRequest.builder()
                        .model(Models.EMBEDDING_001)
                        .content(new Content(null,new Text("Hello"))).build();




        EmbeddingResponse embeddingResponse = model.embedContent(embeddingRequest);

        List<Float> list = embeddingResponse.getEmbedding().getValues();

        list.forEach(System.out::println);

        System.out.println("\n\n");



        // batchEmbedding

        Text text1 = new Text("How are you");

        EmbeddingRequest embeddingRequest1 = EmbeddingRequest.builder()
                        .model(Models.EMBEDDING_001)
                                .content(new Content(null,new Text("How are you"))).build();


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
