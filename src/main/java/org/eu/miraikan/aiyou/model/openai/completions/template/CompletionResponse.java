package org.eu.miraikan.aiyou.model.openai.completions.template;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eu.miraikan.aiyou.types.Part;

import java.util.List;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompletionResponse {
    String id;
    String object;
    Long created;
    String model;
    String system_fingerprint;
    List<Choice> choices;
    Usage usage;


}
