package org.eu.miraikan.aiyou.generativeClient.stream;

import org.eu.miraikan.aiyou.model.ModelAdapter;

import java.io.InputStream;
import java.util.Iterator;

/***
 * Provide intuitional Streaming support
 * Iterator for a stream
 * @param <T>
 */
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
