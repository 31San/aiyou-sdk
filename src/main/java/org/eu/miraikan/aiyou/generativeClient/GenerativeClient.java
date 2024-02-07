package org.eu.miraikan.aiyou.generativeClient;

import java.util.Map;

//wrapper for clients supporting different protocols
public abstract class GenerativeClient {
    Map<String,String> clientConfig;

//    GenerativeResponse generateContent(GenerativeRequest request) throws Exception;
//
//
//
//   default StreamIterator generateStreamContent(GenerativeRequest request) throws Exception{
//       throw new UnsupportedOperationException("generateStreamContent");
//
//   }
//
//   //raw type for flexibility
//   GenerativeClient setGenerativeModel(GenerativeModel model);
//
//    default StreamIterator generateBinaryStreamContent(GenerativeRequest request) throws Exception{
//        throw new UnsupportedOperationException("generateBinaryStreamContent");
//
//    }



    public Map<String, String> getClientConfig() {
        return clientConfig;
    }

    public void setClientConfig(Map<String, String> clientConfig) {
        this.clientConfig = clientConfig;
    }
}
