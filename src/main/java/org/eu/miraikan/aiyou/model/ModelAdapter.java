package org.eu.miraikan.aiyou.model;

import java.io.InputStream;


/**
 * According to Api, build request from model input, convert response to output
 * Mainly for json formation. Use object mapper mixin or manually mapping, maybe serializer
 * @param <T>
 */
public interface ModelAdapter<T> {



    default T handleStream(InputStream is){
        throw new UnsupportedOperationException("handleStream");
    }





}
