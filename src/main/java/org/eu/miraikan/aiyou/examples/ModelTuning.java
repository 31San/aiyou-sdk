package org.eu.miraikan.aiyou.examples;


import org.eu.miraikan.aiyou.constant.Roles;
import org.eu.miraikan.aiyou.generativeClient.RestChatClient;
import org.eu.miraikan.aiyou.model.gemini.template.GeminiRequest;
import org.eu.miraikan.aiyou.model.gemini.template.GeminiResponse;
import org.eu.miraikan.aiyou.model.gemini.tuning.GeminiProTuning;
import org.eu.miraikan.aiyou.support.ClientConfigurationHelper;
import org.eu.miraikan.aiyou.types.Content;
import org.eu.miraikan.aiyou.types.Text;
import org.eu.miraikan.aiyou.types.tuning.*;

import java.io.IOException;
import java.util.List;

public class ModelTuning {
    public static void main(String[] args) throws IOException, InterruptedException {
       ModelTuning modelTuning = new ModelTuning();
       modelTuning.listModel();
       modelTuning.tuneGeminiPro();
    }


    public  void tuneGeminiPro() throws IOException, InterruptedException {

       RestChatClient client = new RestChatClient(ClientConfigurationHelper.createGeminiOAuthClientConfig());

       String PROJECT_ID = System.getenv("PROJECT_ID");
       String ACCESS_TOKEN = System.getenv("ACCESS_TOKEN");

       if(PROJECT_ID!=null){
           client.getClientConfig().put("PROJECT_ID", PROJECT_ID);
       }
       if(ACCESS_TOKEN!=null){
           client.getClientConfig().put("ACCESS_TOKEN",ACCESS_TOKEN);
       }

        GeminiProTuning  geminiProTuning = new GeminiProTuning(client);

       //create a tuning request
        TunedModel tunedModel = TunedModel.builder()
                .displayName("next-number")
                .description("return next number")
                .baseModel("models/gemini-1.0-pro-001")
                .build();

        HyperParameters hyperParameters = HyperParameters.builder()
                .learningRate(0.001f)
                .epochCount(5)
                .batchSize(4).build();

        TrainingData<TuningExampleDict> trainingData = new TrainingData<>();
        TrainingData.Examples<TuningExampleDict> examples = new TrainingData.Examples<>();
        examples.setExamples(List.of(
                new TuningExampleDict("2", "3"),
                new TuningExampleDict("-3", "-2"),
                new TuningExampleDict("twenty two", "twenty three"),
                new TuningExampleDict("two hundred", "two hundred one"),
                new TuningExampleDict("ninety nine", "one hundred"),
                new TuningExampleDict("1,000", "1,001"),
                new TuningExampleDict("one", "two"),
                new TuningExampleDict("three", "four"),
                new TuningExampleDict("seven", "eight"),
                new TuningExampleDict("234", "235"),
                new TuningExampleDict("454", "455"),
                new TuningExampleDict("-1", "0"),
                new TuningExampleDict("0", "1"),
                new TuningExampleDict("一", "二"),
                new TuningExampleDict("三", "四"),
                new TuningExampleDict("九", "十"),
                new TuningExampleDict("一百", "一百零一"),
                new TuningExampleDict("一千", "一千零一"),
                new TuningExampleDict("999", "1000")
        ));
        trainingData.setExamples(examples);

        TuningTask tuningTask = TuningTask.builder()
                .trainingData(trainingData)
                .hyperParameters(hyperParameters)
                .build();
        tunedModel.setTuningTask(tuningTask);


        String name = geminiProTuning.createTunedModel(tunedModel);

        System.out.println("model name: "+name);


        //check task state

        TunedModel tunedModel1 = geminiProTuning.getTunedModel(name);

        while (tunedModel1.getState()!=State.ACTIVE){
            Thread.sleep(10*1000);
            tunedModel1=geminiProTuning.getTunedModel(name);
            if(tunedModel1.getState()==State.STATE_UNSPECIFIED||tunedModel1.getState()==State.FAILED){
                return;
            }
        }

        //test tunedModel

        geminiProTuning.setModelName(name);

        GeminiRequest geminiRequest = new GeminiRequest();
        geminiRequest.setContent(new Content(Roles.ROLE_USER,new Text("520")));


        GeminiResponse geminiResponse = geminiProTuning.generateContent(geminiRequest);

        System.out.println("gemini response");
        System.out.println(geminiResponse.getCandidates().get(0).getContent().getParts().get(0));

        //delete tunedModel
        geminiProTuning.deleteTunedModel(name);



    }

    public void listModel() throws IOException, InterruptedException {
        RestChatClient client = new RestChatClient(ClientConfigurationHelper.createGeminiOAuthClientConfig());

        String PROJECT_ID = System.getenv("PROJECT_ID");
        String ACCESS_TOKEN = System.getenv("ACCESS_TOKEN");

        if(PROJECT_ID!=null){
            client.getClientConfig().put("PROJECT_ID", PROJECT_ID);
        }
        if(ACCESS_TOKEN!=null){
            client.getClientConfig().put("ACCESS_TOKEN",ACCESS_TOKEN);
        }

        GeminiProTuning  geminiProTuning = new GeminiProTuning(client);

        List<TunedModel> tunedModels = geminiProTuning.ListTunedModel();

        for(TunedModel tunedModel:tunedModels){
            System.out.println(tunedModel.getName());
        }

    }
}
