package org.eu.miraikan.aiyou.model.openai.completions.template;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

/**
 * Only for gpt4 vision
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageContent {
    String type;
    String text;
    @JsonProperty("image_url")
    Map<String,String> image_url;

    public String getType() {
        return type;
    }

    public void setTypeText() {
        this.type = "text";
    }

    public void setTypeImage() {
        this.type = "image_url";
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Map<String, String> getImage_url() {
        return image_url;
    }

    public void setImage_url(String url) {
        image_url =  new HashMap<>();
        image_url.put("url",url);
    }

    public void setImageFile(String base64) {
        image_url =  new HashMap<>();
        image_url.put("url","data:image/jpeg;base64,"+base64);
    }
}