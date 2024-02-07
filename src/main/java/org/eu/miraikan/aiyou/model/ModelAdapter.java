package org.eu.miraikan.aiyou.model;

import java.io.InputStream;
import java.util.Iterator;

//according to Api, build request from model input, convert response to output
//mainly for json formation. use object mapper mixin or manually mapping.maybe serializer?
public interface ModelAdapter<T> {

    //optional
//    default HttpRequest createHttpRequest(Map<String,String> config, GenerativeRequest generativeRequest) throws Exception {
//        throw new UnsupportedOperationException("createHttpRequest");
  //      return null;
//    }

//    default HttpRequest createStreamRequest(Map<String,String> config, GenerativeRequest generativeRequest) throws Exception {
//        throw new UnsupportedOperationException("createStreamRequest");
//    }

// even default method can cause erasure and override problem

//    default GenerativeResponse handleHttpResponse(HttpResponse httpResponse) throws Exception{
//        throw new UnsupportedOperationException("handleHttpResponse");
//    }

    default T handleStream(Iterator<String> iterator){
        throw new UnsupportedOperationException("handleStream");
    }


    //must catch IOException
    default byte[] handleStream(InputStream is)  {
        throw new UnsupportedOperationException("handleStream");
    }


}
