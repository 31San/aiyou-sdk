package org.eu.miraikan.aiyou.model.gemini.template;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.eu.miraikan.aiyou.types.Candidate;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeminiResponse {
    List<Candidate> candidates;


    //promptFeedback. not support yet

    public GeminiResponse() {
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }
}
