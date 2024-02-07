package org.eu.miraikan.aiyou.model.openai.completions.template;

public class TextMessage extends Message{
    public TextMessage() {
    }

    public TextMessage(String role, String content) {
        this.role=role;
        this.content=content;
    }

    public String getContent() {
        return (String) content;
    }

    public void setContent(String content) {
        this.content=content;
    }

    @Override
    public String getRole() {
        return role;
    }

    @Override
    public void setRole(String role) {

    }
}
