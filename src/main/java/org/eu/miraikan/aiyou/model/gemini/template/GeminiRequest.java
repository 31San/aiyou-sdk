package org.eu.miraikan.aiyou.model.gemini.template;


import org.eu.miraikan.aiyou.types.Content;

import java.util.List;
import java.util.Map;

//to json
public class GeminiRequest {

    List<Content> contents;
   GenerationConfig generationConfig;




    public GeminiRequest() {
    }


    public GenerationConfig getGenerationConfig() {
        return generationConfig;
    }

    public void setGenerationConfig(GenerationConfig generationConfig) {
        this.generationConfig = generationConfig;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }
// settings
}
