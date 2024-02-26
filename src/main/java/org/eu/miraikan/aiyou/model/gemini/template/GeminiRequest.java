package org.eu.miraikan.aiyou.model.gemini.template;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eu.miraikan.aiyou.types.Content;
import org.eu.miraikan.aiyou.types.functionCalling.Tool;

import java.util.List;

/**
 * Not support SafetySettings yet
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeminiRequest {

    List<Content> contents;
   GenerationConfig generationConfig;


    List<Tool> tools;

    /**
     * Build GeminiRequest with single content
      * @param content
     * @return
     */
    public GeminiRequest setContent(Content content) {
        this.contents = List.of(content);
        return this;
    }


}
