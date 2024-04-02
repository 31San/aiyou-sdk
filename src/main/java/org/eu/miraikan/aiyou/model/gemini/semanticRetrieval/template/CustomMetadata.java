package org.eu.miraikan.aiyou.model.gemini.semanticRetrieval.template;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomMetadata {
    private String key;
    private String stringValue;
    private StringList stringListValue;
    private Number numericValue;

    public CustomMetadata(String key, String stringValue) {
        this.key = key;
        this.stringValue = stringValue;
    }

    public CustomMetadata(String key,  StringList stringListValue) {
        this.key = key;

        this.stringListValue = stringListValue;
    }

    public CustomMetadata(String key, Number numericValue) {
        this.key = key;
        this.numericValue = numericValue;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class StringList {
        private List<String> values;

        // getter and setter for values
    }
}
