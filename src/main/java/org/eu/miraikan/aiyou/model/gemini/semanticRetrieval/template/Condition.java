package org.eu.miraikan.aiyou.model.gemini.semanticRetrieval.template;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Condition {
    Operator operation;
    String stringValue;
    Number numericValue;

    public Condition(String stringValue, Operator operation) {
        this.stringValue = stringValue;
        this.operation = operation;
    }

    public Condition(Number numericValue, Operator operation) {
        this.numericValue = numericValue;
        this.operation = operation;
    }

    public static enum Operator {
        OPERATOR_UNSPECIFIED,
        LESS,
        LESS_EQUAL,
        EQUAL,
        GREATER_EQUAL,
        GREATER,
        NOT_EQUAL,
        INCLUDES,
        EXCLUDES;
    }
}
