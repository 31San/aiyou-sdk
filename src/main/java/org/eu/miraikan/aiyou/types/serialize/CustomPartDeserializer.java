package org.eu.miraikan.aiyou.types.serialize;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.eu.miraikan.aiyou.types.Part;
import org.eu.miraikan.aiyou.types.Text;
import org.eu.miraikan.aiyou.types.functionCalling.FunctionCall;

import java.io.IOException;
import java.util.Map;

public class CustomPartDeserializer extends StdDeserializer<Part>{
    protected CustomPartDeserializer() {
        super(Part.class);
    }

    @Override
    public Part deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException, JacksonException {

        // 获得当前token的起始位置
        jp.nextToken();
        // 构建一个JsonNode
        JsonNode node = jp.getCodec().readTree(jp);

        // 注意，以下我们使用JsonNode来获取相关属性，而不是treeToValue
        if (node.has("text")) {
            Text textPart = new Text();
            textPart.setData(node.get("text").asText());
            return textPart;
        } else if (node.has("functionCall")) {
            FunctionCall functionCall = new FunctionCall();
            functionCall.setName(node.get("functionCall").get("name").asText());

            // 将 args 节点转换回 JSON 字符串
            String argsAsJsonString = node.get("functionCall").get("args").toString();
            functionCall.setArgs(argsAsJsonString);
            return functionCall;
        } else {
            throw new IOException("Cannot deserialize Part");
        }
    }


}
