package org.eu.miraikan.aiyou.types.functionCalling;

import org.eu.miraikan.aiyou.types.Part;

public class FunctionCall extends Part {
    String name;
    String args;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }
}
