package org.eu.miraikan.aiyou.support;

import java.util.HashMap;
import java.util.Map;

//deprecated?
//Model configuration template for supporting platform
public class ModelConfigurationHelper {



    //gemini support only 1 candidate, so candidateCount does not set;
    public static Map<String,String> createGeminiModelConfig(){
        Map<String,String> config = new HashMap<>();

        // Values can range from [0.0,1.0]
        config.put("temperature","0.9");
        config.put("maxOutputTokens","800");
        //The default value varies
        config.put("topP", "0.8");
        config.put("topK", "10");
        //Optional. The set of character sequences (up to 5) that will stop output
        config.put("stopSequences", "[]");
        return config;
    }
}
