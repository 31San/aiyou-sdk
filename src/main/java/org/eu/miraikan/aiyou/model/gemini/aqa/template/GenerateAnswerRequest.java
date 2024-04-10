package org.eu.miraikan.aiyou.model.gemini.aqa.template;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.eu.miraikan.aiyou.model.gemini.template.SafetySetting;
import org.eu.miraikan.aiyou.types.Content;

import java.util.List;

/**
 * Each GenerateAnswerRequest can have one of inlinePassages and SemanticRetrieverConfig.
 * safetySettings,temperature are optional.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenerateAnswerRequest {
    List<Content> contents;
    AnswerStyle answerStyle;
    List<SafetySetting> safetySettings;
    GroundingPassages inlinePassages;
    SemanticRetrieverConfig semanticRetriever;
    Number temperature;

    public GenerateAnswerRequest(List<Content> contents,AnswerStyle answerStyle,
                                 GroundingPassages groundingPassages){
        this.contents=contents;
        this.answerStyle=answerStyle;
        this.inlinePassages=groundingPassages;
    }
    public GenerateAnswerRequest(List<Content> contents,AnswerStyle answerStyle,
                                 SemanticRetrieverConfig semanticRetriever){
        this.contents=contents;
        this.answerStyle=answerStyle;
        this.semanticRetriever=semanticRetriever;
    }


    public static enum AnswerStyle{
        ANSWER_STYLE_UNSPECIFIED,
        ABSTRACTIVE,
        EXTRACTIVE,
        VERBOSE

    }
}
