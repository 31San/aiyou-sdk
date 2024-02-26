package org.eu.miraikan.aiyou.types;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Blob extends Part {


    public Blob() {
    }

    public Blob(String mime_type, byte[] data) {

       InlineData inlineData = new InlineData(mime_type, data);
       this.data=inlineData;
    }



    @JsonProperty("inline_data")
    @Override
    public InlineData getData() {
        return (InlineData) this.data;
    }



    public static class InlineData{
        private String mime_type;

        public InlineData() {
        }

        public String getMime_type() {
            return mime_type;
        }

        public void setMime_type(String mime_type) {
            this.mime_type = mime_type;
        }

        public byte[] getData() {
            return data;
        }

        public void setData(byte[] data) {
            this.data = data;
        }

        private byte[] data;
        public InlineData(String mime_type, byte[] data) {
            this.mime_type = mime_type;
            this.data = data;
        }
    }
}
