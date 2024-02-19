package org.eu.miraikan.aiyou.generativeClient.stream;

import org.eu.miraikan.aiyou.model.ModelAdapter;

import java.io.InputStream;
import java.util.Iterator;

//only for text stream, including base64
public class StreamIterator<T>  implements Iterator<T> {
    InputStream is;
    ModelAdapter<T> modelAdapter;
    T cache;

    public StreamIterator(InputStream is, ModelAdapter<T> modelAdapter) {

        this.modelAdapter = modelAdapter;
        this.is = is;
    }



    @Override
    public boolean hasNext() {

        T t =  modelAdapter.handleStream(is);
        if(t!=null){
            this.cache=t;
            return true;
        }
        return false;
    }

    @Override
    public T next() {
        if(cache==null){
            return  modelAdapter.handleStream(is);
        }
        return cache;
    }
}
