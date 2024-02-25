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

    public FunctionDeclaration setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public FunctionDeclaration setDescription(String description) {

        this.description = description;
        return this;
    }

    public Class<?> getParameters() {
        return parameters;
    }

    public void setParameters(Class<?> parameters) {
        this.parameters = parameters;
    }
}
