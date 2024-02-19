package org.eu.miraikan.aiyou.model;

import java.io.InputStream;
import java.util.Iterator;

//according to Api, build request from model input, convert response to output
//mainly for json formation. use object mapper mixin or manually mapping.maybe serializer?
public interface ModelAdapter<T> {



    default T handleStream(InputStream is){
        throw new UnsupportedOperationException("handleStream");
    }


    //must catch IOException
    default byte[] handleBinaryStream(InputStream is)  {
        throw new UnsupportedOperationException("handleStream");
    }


}
