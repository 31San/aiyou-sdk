package org.eu.miraikan.aiyou.types;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


@JsonDeserialize(as = Text.class)
public abstract class Part {
    protected Object data;

    public Part() {
    }

    public Part(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
