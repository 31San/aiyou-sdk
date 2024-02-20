package org.eu.miraikan.aiyou.model.gemini.template;

import org.eu.miraikan.aiyou.types.Embedding;

import java.util.List;

public class BatchEmbeddingResponse {
    List<Embedding> Embeddings;

    public List<Embedding> getEmbeddings() {
        return Embeddings;
    }

    public void setEmbeddings(List<Embedding> embeddings) {
        Embeddings = embeddings;
    }
}
