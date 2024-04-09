package org.eu.miraikan.aiyou.model.gemini.template;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SafetySetting {
    HarmCategory category;
    HarmBlockThreshold threshold;


    public static enum HarmCategory{

        HARM_CATEGORY_UNSPECIFIED,
        HARM_CATEGORY_DEROGATORY,
        HARM_CATEGORY_TOXICITY,
        HARM_CATEGORY_VIOLENCE,
        HARM_CATEGORY_SEXUAL,
        HARM_CATEGORY_MEDICAL,
        HARM_CATEGORY_DANGEROUS,
        HARM_CATEGORY_HARASSMENT,
        HARM_CATEGORY_HATE_SPEECH,
        HARM_CATEGORY_SEXUALLY_EXPLICIT,
        HARM_CATEGORY_DANGEROUS_CONTENT;

    }
    public static enum HarmBlockThreshold{
        HARM_BLOCK_THRESHOLD_UNSPECIFIED,
        BLOCK_LOW_AND_ABOVE	,
        BLOCK_MEDIUM_AND_ABOVE,
        BLOCK_ONLY_HIGH	,
        BLOCK_NONE	;
    }

}
