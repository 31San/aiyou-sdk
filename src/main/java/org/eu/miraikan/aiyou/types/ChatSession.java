package org.eu.miraikan.aiyou.types;

import java.util.ArrayList;
import java.util.List;

//saving history
//for txt, only one choice of each should be saved
public class ChatSession<T> {
    List<T> contents;

    public ChatSession() {
        this.contents = new ArrayList<T>();
    }

    public List<T> getContents() {
        return contents;
    }

    public void setContents(List<T> contents) {
        this.contents = contents;
    }

    public void append(T content) {
        contents.add(content);
    }


}
