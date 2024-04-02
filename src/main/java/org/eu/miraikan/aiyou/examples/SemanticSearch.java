package org.eu.miraikan.aiyou.examples;


import org.eu.miraikan.aiyou.generativeClient.RestChatClient;
import org.eu.miraikan.aiyou.model.gemini.semanticRetrieval.Retriever;
import org.eu.miraikan.aiyou.model.gemini.semanticRetrieval.template.*;
import org.eu.miraikan.aiyou.support.ClientConfigurationHelper;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

//Create,query,delete corpus.
 //Use AQA model for generation.
public class SemanticSearch {
    public static void main(String[] args) throws IOException, InterruptedException {
        SemanticSearch semanticSearch = new SemanticSearch();
      //  semanticSearch.create();
        semanticSearch.listAndGet();
   //   semanticSearch.query();
    //    semanticSearch.delete();
    }
    //create Chunks
    public void create() throws IOException, InterruptedException {
        RestChatClient client = createClient();
        Retriever retriever = new Retriever(client);
        Corpus corpus = retriever.createCorpus(Optional.of("testing1"),Optional.of("Google for Developers Blog"));
        String docName= "Introducing Project IDX, An Experiment to Improve Full-stack, Multiplatform App Development";
        String docMetadata="https://developers.googleblog.com/2023/08/introducing-project-idx-experiment-to-improve-full-stack-multiplatform-app-development.html";
        Document document = retriever.createDocument(corpus.getName(),Optional.empty(),Optional.of(docName),
                Optional.of(List.of(new CustomMetadata("url",docMetadata))));

        Chunk chunk1 = new Chunk();
        chunk1.setChunkData("Chunks support user specified metadata.");
        chunk1.setCustomMetadata(List.of(new CustomMetadata("section","Custom metadata filters")));

        Chunk chunk2 = new Chunk();
        chunk2.setChunkData("The maximum number of metadata supported is 20");
        chunk1.setCustomMetadata(List.of(new CustomMetadata("num_keys",20)));

        List<Chunk> chunks = retriever.batchCreateChunks(document.getName(),List.of(chunk1,chunk2));

        chunks.forEach(System.out::println);
    }


    private RestChatClient createClient(){
        RestChatClient client = new RestChatClient(ClientConfigurationHelper.createGeminiOAuthClientConfig());

        String PROJECT_ID = System.getenv("PROJECT_ID");
        String ACCESS_TOKEN = System.getenv("ACCESS_TOKEN");

        if(PROJECT_ID!=null){
            client.getClientConfig().put("PROJECT_ID", PROJECT_ID);
        }
        if(ACCESS_TOKEN!=null){
            client.getClientConfig().put("ACCESS_TOKEN",ACCESS_TOKEN);
        }
        return client;
    }

    //Get operation are redundant
    public void listAndGet() throws IOException, InterruptedException {
        RestChatClient client = createClient();
        Retriever retriever = new Retriever(client);

        List<Corpus> corpusList = retriever.listCorpora();

        Corpus corpus = retriever.getCorpus(corpusList.get(0).getName());

        List<Document> documentList = retriever.listDocuments(corpus.getName());
        Document document = retriever.getDocument(documentList.get(0).getName());
        List<Chunk> chunkList = retriever.listChunks(document.getName());
        Chunk chunk = retriever.getChunk(chunkList.get(0).getName());
        chunkList.forEach(System.out::println);

    }

    public void query() throws IOException, InterruptedException {
        RestChatClient client = createClient();
        Retriever retriever = new Retriever(client);
        String userQuery="What is the purpose of Project IDX?";
        int resultCount =5;
        MetadataFilter metadataFilter = new MetadataFilter("chunk.custom_metadata.num_keys",
                List.of(new Condition(20,Condition.Operator.EQUAL)));

        List<RelevantChunk> relevantChunks = retriever.query("corpora/testing1",userQuery,
                Optional.of(List.of(metadataFilter)),Optional.of(resultCount));
        relevantChunks.forEach(System.out::println);
    }
    public void delete() throws IOException, InterruptedException {
        RestChatClient client = createClient();
        Retriever retriever = new Retriever(client);
        List<Corpus> corpusList = retriever.listCorpora();
        List<Document> documentList = retriever.listDocuments(corpusList.get(0).getName());
        List<Chunk> chunkList = retriever.listChunks(documentList.get(0).getName());
        for(Chunk chunk:chunkList){
            retriever.deleteChunk(chunk.getName());
        }
        retriever.deleteDocument(documentList.get(0).getName(),true);

        retriever.deleteCorpus(corpusList.get(0).getName(),true);
    }
}
