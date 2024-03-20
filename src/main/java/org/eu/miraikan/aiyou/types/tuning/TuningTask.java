package org.eu.miraikan.aiyou.types.tuning;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TuningTask {
    Date startTime;
    Date completeTime;

    List<Snapshot> snapshots;
    @JsonProperty("hyperparameters")
    HyperParameters hyperParameters;

    @JsonProperty("training_data")
    TrainingData trainingData;
}
