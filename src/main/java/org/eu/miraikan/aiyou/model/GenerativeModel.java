package org.eu.miraikan.aiyou.model;



import java.util.Iterator;
import java.util.List;


//empty interface
public interface  GenerativeModel {



  //  protected GenerativeClient client;
    //neglectable. default using active mode
    //protected GenerationConfig generationConfig;
//    protected ModelAdapter modelAdapter;



    //for override
//    default  GenerativeResponse generateContent(GenerativeRequest generativeRequest) throws Exception{
//        throw new UnsupportedOperationException("generateContent");
//    };



//    default Iterator<String> generateStreamContent(GenerativeRequest generativeRequest) throws Exception{
//        throw new UnsupportedOperationException("generateStreamContent");
//    };



//    default Iterator<byte[]> generateBinaryStreamContent(GenerativeRequest generativeRequest) throws Exception{
//        throw new UnsupportedOperationException("generateBinaryStreamContent");
//    };

    //not support yet?
//    default List<Float> embedContent(GenerativeRequest generativeRequest){
//        throw new UnsupportedOperationException("embedContent");
//    }

//    default List<List<Float> > batchEmbedContents(GenerativeRequest generativeRequest){
//        throw new UnsupportedOperationException("batchEmbedContents");
//    }

//    default int countTokens(GenerativeRequest generativeRequest){
//        throw new UnsupportedOperationException("countTokens");
//    }

//    public void setClient(GenerativeClient client) {
//        this.client=client;
//
//    }

//    public ModelAdapter getModelAdapter() {
//        return modelAdapter;
//    }



}
