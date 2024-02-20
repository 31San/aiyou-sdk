package org.eu.miraikan.aiyou.model.gemini.template;

import org.eu.miraikan.aiyou.types.Embedding;

public class EmbeddingResponse {
    Embedding embedding;

    public Embedding getEmbedding() {
        return embedding;
    }

    public void setEmbedding(Embedding embedding) {
        this.embedding = embedding;
    }
}
