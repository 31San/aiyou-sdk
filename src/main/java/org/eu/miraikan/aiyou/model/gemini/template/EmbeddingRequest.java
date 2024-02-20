package org.eu.miraikan.aiyou.model.gemini.template;

import org.eu.miraikan.aiyou.types.Content;

public class EmbeddingRequest {
    String model;
    String taskType;
    String title;
    //do not need user property
    Content content;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}
