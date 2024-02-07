package org.eu.miraikan.aiyou.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//name from gemini response
@JsonIgnoreProperties(ignoreUnknown = true)
public class Candidate {
    int index;
    Content content;
    String finishReason;
    int tokenCount;
    //SafetyRating. not support yet.


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public String getFinishReason() {
        return finishReason;
    }

    public void setFinishReason(String finishReason) {
        this.finishReason = finishReason;
    }

    public int getTokenCount() {
        return tokenCount;
    }

    public void setTokenCount(int tokenCount) {
        this.tokenCount = tokenCount;
    }
}
