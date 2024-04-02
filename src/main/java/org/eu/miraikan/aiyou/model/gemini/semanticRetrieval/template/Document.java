package org.eu.miraikan.aiyou.model.gemini.semanticRetrieval.template;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * A Corpus can have a maximum of 10,000 Documents.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Document {
    String name;
    String displayName;
    Date createTime;
    Date updateTime;
    List<CustomMetadata> customMetadata;
}
