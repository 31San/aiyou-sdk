package org.eu.miraikan.aiyou.support;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eu.miraikan.aiyou.types.functionCalling.FunctionDeclaration;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;

public class FunctionCallingHelper {

    List<FunctionDeclaration> functionDeclarations = new ArrayList<>();

    Map<String,Class<?>> parameterTypeMap =new HashMap<>();
    Map<String,Object> instanceMap = new HashMap<>();



    ObjectMapper objectMapper = new ObjectMapper();

    Executor executor;

    public <T>FunctionDeclaration addFunction(String name,Class<T> parameterType,Function<T,Object> function){
        FunctionDeclaration functionDeclaration = new FunctionDeclaration();

        functionDeclaration.setParameters(parameterType);
            functionDeclaration.setName(name);

        instanceMap.put(name,function);
        parameterTypeMap.put(name,parameterType);


        functionDeclarations.add(functionDeclaration);
        return functionDeclaration;
    }





    public List<FunctionDeclaration> getFunctionDeclarations(){

        return functionDeclarations;
    }





    public  Object callFunction(String name, String args) throws JsonProcessingException, InvocationTargetException, IllegalAccessException {

        Class<?> parameterType = parameterTypeMap.get(name);

        Function instance = (Function)instanceMap.get(name);
        return instance.apply(objectMapper.readValue(args,parameterType));


    }

    public CompletableFuture<Object> executeAsync(String name, String args) throws JsonProcessingException {
        Class<?> parameterType = parameterTypeMap.get(name);

        Function instance = (Function)instanceMap.get(name);

        Object argument = objectMapper.readValue(args,parameterType);


        CompletableFuture<Object> future =null;

        if(executor!=null){
            future     = CompletableFuture.supplyAsync(() -> {
                return  instance.apply(argument);
            },executor);
        }else {
            future     = CompletableFuture.supplyAsync(() -> {
                return  instance.apply(argument);
            });
        }



        return future;
    }

}
