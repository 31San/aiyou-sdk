package org.eu.miraikan.aiyou.model.gemini.tuning;


import org.eu.miraikan.aiyou.generativeClient.RestChatClient;
import org.eu.miraikan.aiyou.model.gemini.GeminiPro;
import org.eu.miraikan.aiyou.types.tuning.TunedModel;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class GeminiProTuning extends GeminiPro {

    GeminiTuningAdapter modelAdapter;

    public static final String MODEL_NAME = "models/gemini-pro";

    public GeminiProTuning(RestChatClient client) {
       modelAdapter=new GeminiTuningAdapter(client.getClientConfig(),MODEL_NAME);
        super.setClient(client);
        super.setModelAdapter(modelAdapter);

    }

    public GeminiProTuning(RestChatClient client, String modelName) {
        modelAdapter=new GeminiTuningAdapter(client.getClientConfig(),modelName);
        super.setClient(client);
        super.setModelAdapter(modelAdapter);
    }

    public TunedModel getTunedModel(String modelName) throws IOException, InterruptedException {
        HttpRequest httpRequest = modelAdapter.createGetModelRequest(modelName);
        HttpResponse<String> httpResponse = client.generateContent(httpRequest);
         return modelAdapter.handleGetModelRequest(httpResponse);


    }
    public List<TunedModel> ListTunedModel() throws IOException, InterruptedException {
        HttpRequest httpRequest = modelAdapter.createListTunedModelsRequest();
        HttpResponse<String> httpResponse = client.generateContent(httpRequest);;
        return modelAdapter.handleListTunedModelsResponse(httpResponse);

    }

    /**
     *
     * @return model name
     */
    public String createTunedModel(TunedModel tunedModel) throws IOException, InterruptedException {

        HttpRequest httpRequest = modelAdapter.createTunedModelRequest(tunedModel);

        //send httpRequest and get text response
        HttpResponse<String> httpResponse = client.generateContent(httpRequest);

        return modelAdapter.handleCreateTunedModelResponse(httpResponse);

    }

    /**
     * Update model description.
     * Not support yet
     */
    public void updateTunedModel(){}

    public void deleteTunedModel(String ModelName) throws IOException, InterruptedException {
        HttpRequest httpRequest = modelAdapter.createDeleteModelRequest(ModelName);
        HttpResponse<String> httpResponse = client.generateContent(httpRequest);

    }




    public void setModelName(String modelName){
        modelAdapter.setModelName(modelName);
    }

    public String getModelName(){
       return modelAdapter.getModelName();
    }
}
