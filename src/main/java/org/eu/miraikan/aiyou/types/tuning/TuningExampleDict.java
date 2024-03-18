package org.eu.miraikan.aiyou.types.tuning;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TuningExampleDict {
    String text_input;
    String output;
//    public TuningExampleDict(String input, String output) {
//        this.text_input = "input: "+input;
//        this.output = "output: "+output;
//    }
}
