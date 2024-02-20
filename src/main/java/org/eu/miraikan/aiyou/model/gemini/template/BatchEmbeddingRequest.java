package org.eu.miraikan.aiyou.model.gemini.template;

import java.util.List;

public class BatchEmbeddingRequest {
    List<EmbeddingRequest> requests;

    public List<EmbeddingRequest> getRequests() {
        return requests;
    }

    public void setRequests(List<EmbeddingRequest> requests) {
        this.requests = requests;
    }
}
