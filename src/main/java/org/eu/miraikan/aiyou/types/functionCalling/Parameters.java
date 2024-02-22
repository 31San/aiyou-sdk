package org.eu.miraikan.aiyou.types.functionCalling;

import java.util.List;
import java.util.Map;

public class Parameters {
    //valid json type
    String type;
    Map<String,Property> properties;
    List<String> required;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Property> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Property> properties) {
        this.properties = properties;
    }

    public List<String> getRequired() {
        return required;
    }

    public void setRequired(List<String> required) {
        this.required = required;
    }
}
