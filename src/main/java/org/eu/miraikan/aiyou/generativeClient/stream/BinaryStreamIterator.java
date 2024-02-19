package org.eu.miraikan.aiyou.generativeClient.stream;

import org.eu.miraikan.aiyou.model.ModelAdapter;


import java.io.InputStream;
import java.util.Iterator;

public class BinaryStreamIterator implements Iterator<byte[]> {
    ModelAdapter modelAdapter;
    InputStream is;


    byte[] cache;
    public BinaryStreamIterator(ModelAdapter modelAdapter, InputStream is) {
        this.modelAdapter = modelAdapter;
        this.is = is;

    }

    @Override
    public boolean hasNext() {
        byte[] bytes = modelAdapter.handleBinaryStream(is);
        if(bytes==null){
            return false;
        }
        this.cache=bytes;
        return true;
    }

    @Override
    public byte[] next()  {
       if(cache==null){
           return modelAdapter.handleBinaryStream(is);
       }
        return this.cache;
    }
}
