package org.eu.miraikan.aiyou.support;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eu.miraikan.aiyou.types.functionCalling.FunctionDeclaration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;

public class FunctionCallingHelper {
//    List<Class<?>> functionList = new ArrayList<>();
    List<FunctionDeclaration> functionDeclarations = new ArrayList<>();

    Map<String,Class<?>> parameterTypeMap =new HashMap<>();
    Map<String,Object> instanceMap = new HashMap<>();
//    Map<String,Method> functionMap = new HashMap<>();


    ObjectMapper objectMapper = new ObjectMapper();



    public <T>FunctionDeclaration addFunction(String name,Class<T> parameterType,Function<T,Object> function){
        FunctionDeclaration functionDeclaration = new FunctionDeclaration();
      //  String name = function.getClass().getMethods()[0].getName();
        functionDeclaration.setParameters(parameterType);
            functionDeclaration.setName(name);
     //   functionMap.put(name,method);
        instanceMap.put(name,function);
        parameterTypeMap.put(name,parameterType);


        functionDeclarations.add(functionDeclaration);
        return functionDeclaration;
    }

//    public void addMethodDefinition(Class<?> clazz){
//        functionList.add(clazz);
//        FunctionDeclaration functionDeclaration = new FunctionDeclaration();
//        Method method = clazz.getMethods()[0];
//        if (method.getParameterTypes().length != 1 || !Object.class.isAssignableFrom(method.getParameterTypes()[0])) {
//            throw new IllegalArgumentException("Method must have only one Object-type parameter");
//        }
//
//        String description = method.getAnnotation(JsonPropertyDescription.class).value();
//        String name = method.getName();
//
//        functionDeclaration.setParameters(method.getParameterTypes()[0]);
//        functionDeclaration.setName(name);
//        functionDeclaration.setDescription(description);
//        functionDeclarations.add(functionDeclaration);
//
//        functionMap.put(name,method);
//
//        parameterTypeMap.put(name,method.getParameterTypes()[0]);
//
//
//    }



    public List<FunctionDeclaration> getFunctionDeclarations(){

        return functionDeclarations;
    }



//    public Object getInstance(String functionName,String json) throws JsonProcessingException {
//        Class<?> clazz = parameterTypeMap.get(functionName);
//        return objectMapper.readValue(json,clazz);
//
//    }

//    public void addFunction(Object object) {
//        Class<?> clazz = object.getClass();
//        instanceMap.put(clazz.getMethods()[0].getName(),object);
//    }

    public  Object callFunction(String name, String args) throws JsonProcessingException, InvocationTargetException, IllegalAccessException {

        Class<?> parameterType = parameterTypeMap.get(name);

        Function instance = (Function)instanceMap.get(name);
        return instance.apply(objectMapper.readValue(args,parameterType));
      //  Method method = instance.getClass().getMethods()[0];

      //  return method.invoke(instance,objectMapper.readValue(args,parameterType));


    }

//    public Class<?> getParameterType(String name) {
//        return parameterTypeMap.get(name);
//    }




}
