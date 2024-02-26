package org.eu.miraikan.aiyou.types.functionCalling;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eu.miraikan.aiyou.types.serialize.FunctionParametersSerializer;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FunctionDeclaration {
    String name;
    String description;

    @JsonSerialize(using = FunctionParametersSerializer.class)
    Class<?> parameters;

        public FunctionDeclaration setDescription(String description) {

        this.description = description;
        return this;
    }

}
