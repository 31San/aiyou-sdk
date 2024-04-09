package org.eu.miraikan.aiyou.examples;

import org.eu.miraikan.aiyou.generativeClient.RestChatClient;
import org.eu.miraikan.aiyou.model.gemini.aqa.AQAModel;
import org.eu.miraikan.aiyou.model.gemini.aqa.template.*;
import org.eu.miraikan.aiyou.support.ClientConfigurationHelper;
import org.eu.miraikan.aiyou.types.Content;
import org.eu.miraikan.aiyou.types.Text;

import java.io.IOException;
import java.util.List;

public class AQAExample {
    public static void main(String[] args) throws IOException, InterruptedException {
        AQAExample aqaExample = new AQAExample();
        aqaExample.semanticRetrieve();

        aqaExample.withInlinePassage();

    }

    // A existing corpus is required
    public void semanticRetrieve() throws IOException, InterruptedException {
        RestChatClient client = new RestChatClient(ClientConfigurationHelper.createGeminiOAuthClientConfig());

        String PROJECT_ID = System.getenv("PROJECT_ID");
        String ACCESS_TOKEN = System.getenv("ACCESS_TOKEN");

        if(PROJECT_ID!=null){
            client.getClientConfig().put("PROJECT_ID", PROJECT_ID);
        }
        if(ACCESS_TOKEN!=null){
            client.getClientConfig().put("ACCESS_TOKEN",ACCESS_TOKEN);
        }
        AQAModel aqaModel = new AQAModel(client);
        SemanticRetrieverConfig semanticRetrieverConfig =  SemanticRetrieverConfig.builder()
                .source("corpora/testing1")
                .query(new Content(null,new Text("What is the purpose of Project IDX?")))
                .build();
        List<Content> query= List.of(new Content(null,new Text("What is the maximum number of chunk")));
        GenerateAnswerRequest generateAnswerRequest = new GenerateAnswerRequest(query,
                GenerateAnswerRequest.AnswerStyle.VERBOSE,semanticRetrieverConfig);
        GenerateAnswerResponse generateAnswerResponse = aqaModel.generateAnswer(generateAnswerRequest);
        System.out.println(generateAnswerResponse.getAnswer().getContent().getParts().get(0));
    }


    public void withInlinePassage() throws IOException, InterruptedException {
        RestChatClient client = new RestChatClient(ClientConfigurationHelper.createGeminiClientConfig());
        AQAModel aqaModel = new AQAModel(client);
        List<Content> query= List.of(new Content(null,new Text("What is AQA from Google")));
        GroundingPassage passageA = GroundingPassage.builder()
                .id("001")
                .content(new Content(null, new Text("Attributed Question and Answering (AQA) refers to " +
                        "answering questions grounded to a given corpus and providing citation")))
                .build();
        GroundingPassage passageB = GroundingPassage.builder()
                .id("002")
                .content(new Content(null, new Text("An LLM is not designed to generate content grounded in " +
                        "a set of passages. Although instructing an LLM to answer questions only based on a " +
                        "set of passages reduces hallucination, hallucination still often occurs when" +
                        " LLMs generate responses unsupported by facts provided by passages")))
                .build();
        GroundingPassage passageC = GroundingPassage.builder()
                .id("003")
                .content(new Content(null, new Text("Hallucination is one of the biggest problems in Large" +
                        " Language Models (LLM) development. Large Language Models (LLMs) could produce responses that" +
                        " are fictitious and incorrect, which significantly impacts the usefulness and trustworthiness " +
                        "of applications built with language models.")))
                .build();

        GenerateAnswerRequest generateAnswerRequest = new GenerateAnswerRequest(query,
                GenerateAnswerRequest.AnswerStyle.VERBOSE,new GroundingPassages(List.of(passageA,passageB,passageC)));

        GenerateAnswerResponse generateAnswerResponse = aqaModel.generateAnswer(generateAnswerRequest);
        System.out.println(generateAnswerResponse.getAnswer().getContent().getParts().get(0));

    }


}
