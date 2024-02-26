package org.eu.miraikan.aiyou.model.gemini.template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eu.miraikan.aiyou.types.Content;

/**
 * Don't set role for content
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmbeddingRequest {
    String model;
    String taskType;
    String title;
    Content content;


}
