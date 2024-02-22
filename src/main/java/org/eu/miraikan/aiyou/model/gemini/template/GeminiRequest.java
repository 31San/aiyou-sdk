package org.eu.miraikan.aiyou.model.gemini.template;


import com.fasterxml.jackson.annotation.JsonInclude;
import org.eu.miraikan.aiyou.types.Content;
import org.eu.miraikan.aiyou.types.functionCalling.Tool;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeminiRequest {

    List<Content> contents;
   GenerationConfig generationConfig;


    List<Tool> tools;


    public List<Tool> getTools() {
        return tools;
    }

    public void setTools(List<Tool> tools) {
        this.tools = tools;
    }

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
