package org.eu.miraikan.aiyou.types.functionCalling;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * currently only contain element List<FunctionDeclaration>
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
