package org.eu.miraikan.aiyou.model.openai.completions.template;


import java.util.List;


/**
 * Gtp4 vision message.
 */
public class VisionMessage extends Message{
    String role;


    public VisionMessage() {
    }


    @Override
    public String getRole() {
        return role;
    }

    @Override
    public void setRole(String role) {
        this.role = role;
    }

    //should be independent
    @Override
    public List<MessageContent> getContent() {
        return (List<MessageContent>) content;
    }


    public void setContent(List<MessageContent> content){
        this.content=content;
    }


}
