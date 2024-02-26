package org.eu.miraikan.aiyou.model.openai.completions.template;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Choice{
    Integer index;
    TextMessage message;
    String logprobs;
    String finish_reason;



}