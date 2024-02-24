package org.eu.miraikan.aiyou.types.functionCalling;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Parameter {


    /*
     *   any valid json type
     *   object,number,string,boolean,array
     */
    String type;

    List<String> required;

    //for object
    Map<String,Parameter> properties;

    String description;

    //for array
    Parameter items;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public List<String> getRequired() {
        return required;
    }

    public Map<String, Parameter> getProperties() {
        return properties;
    }

    public String getDescription() {
        return description;
    }

    public Parameter getItems() {
        return items;
    }

    public void setProperties(Map<String, Parameter> properties) {
        this.properties = properties;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setItems(Parameter items) {
        this.items = items;
    }

    public void setRequired(List<String> required) {
        this.required = required;
    }


}
