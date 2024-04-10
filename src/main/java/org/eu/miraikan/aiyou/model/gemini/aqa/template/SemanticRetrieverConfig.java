package org.eu.miraikan.aiyou.model.gemini.aqa.template;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eu.miraikan.aiyou.model.gemini.semanticRetrieval.template.MetadataFilter;
import org.eu.miraikan.aiyou.types.Content;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SemanticRetrieverConfig {
    /**
     * name of corpus or document.
     */
    String source;
    Content query;
    /**
     * Optional
     */
    List<MetadataFilter> metadataFilters;
    /**
     * Optional
     */
    Integer maxChunksCount;
    /**
     * Optional
     */
    Number minimumRelevanceScore;
}
