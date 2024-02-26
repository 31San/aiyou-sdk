package org.eu.miraikan.aiyou.types.functionCalling;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eu.miraikan.aiyou.types.Part;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FunctionCall extends Part {
    String name;
    String args;

}
