package org.eu.miraikan.aiyou.generativeClient.stream;

import org.eu.miraikan.aiyou.model.ModelAdapter;

import java.io.InputStream;
import java.util.Iterator;

//only for text stream, including base64
public class StreamIterator<T>  implements Iterator<T> {
    Iterator<String> stringIterator;
    ModelAdapter<T> modelAdapter;
    T cache;

    public StreamIterator(Iterator<String> stringIterator, ModelAdapter<T> modelAdapter) {
        this.stringIterator = stringIterator;
        this.modelAdapter = modelAdapter;
    }



    @Override
    public boolean hasNext() {

        T t =  modelAdapter.handleStream(stringIterator);
        if(t!=null){
            this.cache=t;
            return true;
        }
        return false;
    }

    @Override
    public T next() {
        if(cache==null){
            return  modelAdapter.handleStream(stringIterator);
        }
        return cache;
    }
}
