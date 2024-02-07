package org.eu.miraikan.aiyou.model.openai.completions.template;


public abstract class Message {
    String role;
    Object content;

    public abstract String getRole();

    public abstract void setRole(String role);

    public Object getContent() {
        return content;
    }


}
