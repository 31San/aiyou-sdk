package org.eu.miraikan.aiyou.model.openai.textToSpeech.template;

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
public class TextToSpeechRequest {

    String model;
    String input;
    String voice;

    /**
     *  Optional
     */
    String response_format;
    Double speed;
    Boolean stream;

}
