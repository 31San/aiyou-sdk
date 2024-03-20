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
public class Snapshot {
    Integer step;
    Integer epoch;
    Float meanLoss;
    Date computeTime;
}
