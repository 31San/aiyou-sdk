package org.eu.miraikan.aiyou.support;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eu.miraikan.aiyou.types.functionCalling.FunctionDeclaration;
import java.lang.reflect.Method;
import java.util.*;

public class FunctionCallingHelper {
    Map<String,Class<?>> functionMap;
    List<Class<?>> functionList;
    List<FunctionDeclaration> functionDeclarations;

    ObjectMapper objectMapper;

    public FunctionCallingHelper() {
        functionMap = new HashMap<>();
        functionList = new ArrayList<>();
        objectMapper = new ObjectMapper();
        functionDeclarations = new ArrayList<>();
    }



    public void addMethodDefinition(Class<?> clazz){
        functionList.add(clazz);
        FunctionDeclaration functionDeclaration = new FunctionDeclaration();
        Method method = clazz.getMethods()[0];
        if (method.getParameterTypes().length != 1 || !Object.class.isAssignableFrom(method.getParameterTypes()[0])) {
            throw new IllegalArgumentException("Method must have only one Object-type parameter");
        }

        String description = method.getAnnotation(JsonPropertyDescription.class).value();
        String name = method.getName();

        functionDeclaration.setParameters(method.getParameterTypes()[0]);
        functionDeclaration.setName(name);
        functionDeclaration.setDescription(description);
        functionDeclarations.add(functionDeclaration);

        //register functionMap
        functionMap.put(name,method.getParameterTypes()[0]);


    }



    public List<FunctionDeclaration> getFunctionDeclarations(){

        return functionDeclarations;
    }



    public Object getInstance(String functionName,String json) throws JsonProcessingException {
        Class<?> clazz = functionMap.get(functionName);
        return objectMapper.readValue(json,clazz);

    }


//    private static Parameter createParameter(Annotation[] annotations, Class<?> clazz) {
//        // Check and assign annotations for the parameter
//        Description description = null;
//        Required required = null;
//
//        for (Annotation annotation : annotations) {
//            if (annotation instanceof Description) {
//                description = (Description) annotation;
//            } else if (annotation instanceof Required) {
//                required = (Required) annotation;
//            }
//        }
//
//        Parameter parameter = new Parameter();
//        //     parameter.setType(parameterType.getSimpleName().toLowerCase());
//        parameter.setDescription(description != null ? description.value() : "");
//
//
//        if (Number.class.isAssignableFrom(clazz) || clazz.equals(int.class) || clazz.equals(long.class) || clazz.equals(double.class) || clazz.equals(float.class)) {
//            parameter.setType("number");
//        }else if (clazz.equals(boolean.class) || clazz.equals(Boolean.class)) {
//            parameter.setType( "boolean");
//        }else if (String.class.isAssignableFrom(clazz)) {
//            parameter.setType("string");
//        }else if (clazz.isArray()) {
//            parameter.setType( "array");
//            parameter.setItems(createParameter(clazz.getComponentType().getAnnotations(),clazz.getComponentType()));
//        }else if (Collection.class.isAssignableFrom(clazz)) {
//            parameter.setType( "array");
//            Type genericSuperclass = clazz.getGenericSuperclass();
//            if (genericSuperclass instanceof ParameterizedType) {
//                ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
//                Parameter items = createParameter(((Class) parameterizedType.getActualTypeArguments()[0]).getAnnotations(),
//                        (Class) parameterizedType.getActualTypeArguments()[0]);
//                parameter.setItems(items);
//            }
//        }else {
//            parameter.setType( "object");
//            Map<String,Parameter> properties = new HashMap<>();
//            for (Field field : clazz.getDeclaredFields()) {
//                Parameter property = createParameter(field.getType().getAnnotations(),field.getType());
//                properties.put(field.getName(), property);
//            }
//
//            parameter.setProperties(properties);
//
//        }
//
//
//
//
//
//
//        return parameter;
//    }

}
