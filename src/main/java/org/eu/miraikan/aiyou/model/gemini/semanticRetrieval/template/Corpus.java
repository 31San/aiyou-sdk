package org.eu.miraikan.aiyou.model.gemini.semanticRetrieval.template;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * A Corpus is a collection of Documents. A project can create up to 5 corpora.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Corpus {

    /**
     * 40 characters that are lowercase alphanumeric or dashes
     */
    String name;
    String displayName;
    Date createTime;
    Date updateTime;





}
