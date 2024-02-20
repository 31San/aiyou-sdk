package org.eu.miraikan.aiyou.support;

import org.eu.miraikan.aiyou.model.gemini.template.GenerationConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ModelConfigurationHelper {



    //gemini support only 1 candidate
    public static GenerationConfig createGeminiModelConfig(){

        GenerationConfig config = new GenerationConfig();
        config.setCandidateCount(1);
        // Values can range from [0.0,1.0]
        config.setTemperature(0.9F);
        config.setMaxOutputTokens(800);
        config.setTopP(0.8F);
        config.setTopK(10);
        //Optional. The set of character sequences (up to 5) that will stop output
        config.setStopSequences(new ArrayList<>());

        return config;
    }
}
