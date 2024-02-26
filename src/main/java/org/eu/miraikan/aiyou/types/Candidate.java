package org.eu.miraikan.aiyou.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Name from gemini response.
 * SafetyRating. not support yet.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Candidate {
    int index;
    Content content;
    String finishReason;
    int tokenCount;



}
