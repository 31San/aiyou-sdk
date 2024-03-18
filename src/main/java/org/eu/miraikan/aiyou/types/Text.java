package org.eu.miraikan.aiyou.types;

import com.fasterxml.jackson.annotation.JsonProperty;



public class Text extends Part {
    public Text() {
    }

    public Text(String data) {
        super(data);
    }

    @JsonProperty("text")
    @Override
    public String getData() {
        return (String) super.getData();
    }

    public void setData(String data) {
        super.setData(data);
    }

    @Override
    public String toString(){
        return (String) super.getData();
    }
}
