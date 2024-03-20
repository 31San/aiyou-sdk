package org.eu.miraikan.aiyou.types.tuning;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TunedModel {
    String name;
    String sourceModel;
    String baseModel;
    String displayName;
    String description;
    Float temperature;
    Float topP;
    Float topK;
    State state;
    Date createTime;
    Date updateTime;
    TuningTask tuningTask;
}
