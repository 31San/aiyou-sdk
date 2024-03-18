package org.eu.miraikan.aiyou.support;

import java.util.HashMap;
import java.util.Map;

/**
 * Client configuration template for supporting platform.
 * Replace API_KEY or API_TOKEN with your own.
 * BASE_URL only needed when use 3rd party service
 */
public class ClientConfigurationHelper {
    public static Map<String,String> createGeminiClientConfig(){
        Map<String,String> config = new HashMap<>();
        config.put("API_KEY","AIzaSyAbZC0KdOyk4avCj-afLzX7x6rxbVnt59A");
        config.put("BASE_URL","https://generativelanguage.googleapis.com");
        return config;
    }

    public static Map<String,String> createGeminiOAuthClientConfig(){
        Map<String,String> config = new HashMap<>();
        config.put("PROJECT_ID","academic-pier-96706");
        config.put("ACCESS_TOKEN","AIzaSyAbZC0KdOyk4avCj-afLzX7x6rxbVnt59A");
        config.put("BASE_URL","https://generativelanguage.googleapis.com");
        return config;
    }

    public static Map<String,String> createWorksAIClientConfig(){
        Map<String,String> config = new HashMap<>();
        config.put("API_TOKEN","mxwfHIdpSuPNQr1Zhi3crsUvFQaJh-igWEFeU1_U");
        config.put("ACCOUNT_ID","e220944b0adf25a44ed786389787fcbe");
        config.put("BASE_URL","https://api.cloudflare.com");
        return config;
    }

    public static Map<String,String> createOpenAIClientConfig(){
        Map<String,String> config = new HashMap<>();
        config.put("OPENAI_API_KEY","mxwfHIdpSuPNQr1Zhi3crsUvFQaJh-igWEFeU1_U");
        config.put("BASE_URL","https://api.openai.com");
        return config;
    }


}
