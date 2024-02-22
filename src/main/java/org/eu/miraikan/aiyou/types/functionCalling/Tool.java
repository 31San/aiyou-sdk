package org.eu.miraikan.aiyou.types.functionCalling;

import java.util.ArrayList;
import java.util.List;

/**
 * currently only contain element List<FunctionDeclaration>
 *
 */
public class Tool   {
    //function_declarations
    List<FunctionDeclaration> functionDeclarations;

    public List<FunctionDeclaration> getFunctionDeclarations() {
        return functionDeclarations;
    }

    public void setFunctionDeclarations(List<FunctionDeclaration> functionDeclarations) {
        this.functionDeclarations = functionDeclarations;
    }
}
