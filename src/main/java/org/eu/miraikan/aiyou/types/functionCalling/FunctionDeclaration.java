package org.eu.miraikan.aiyou.types.functionCalling;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.eu.miraikan.aiyou.types.serialize.FunctionParametersSerializer;


public class FunctionDeclaration {
    String name;
    String description;

    @JsonSerialize(using = FunctionParametersSerializer.class)
    Class<?> parameters;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Class<?> getParameters() {
        return parameters;
    }

    public void setParameters(Class<?> parameters) {
        this.parameters = parameters;
    }
}
