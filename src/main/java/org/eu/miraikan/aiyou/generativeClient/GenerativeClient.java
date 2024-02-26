package org.eu.miraikan.aiyou.generativeClient;

import java.util.Map;

/**
 * Abstract wrapper for clients supporting different protocols
 */
public abstract class GenerativeClient {
    Map<String,String> clientConfig;




    public Map<String, String> getClientConfig() {
        return clientConfig;
    }

    public void setClientConfig(Map<String, String> clientConfig) {
        this.clientConfig = clientConfig;
    }
}
