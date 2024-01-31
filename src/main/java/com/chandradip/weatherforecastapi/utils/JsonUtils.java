package com.chandradip.weatherforecastapi.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public JsonUtils() {
    }

    public static <T> T jsonToJava(JsonNode jsonNode, Class<T> tClass) {
        T object = null;

        try {
            object = OBJECT_MAPPER.treeToValue(jsonNode, tClass);
        } catch (Exception var4) {
            log.info("Method[jsonToJava] Error converting json to java Object value! : {}", var4);
        }

        return object;
    }

    public static JsonNode javaToJson(Object object) {
        JsonNode jsonNode = null;

        try {
            jsonNode = (JsonNode)OBJECT_MAPPER.convertValue(object, JsonNode.class);
        } catch (Exception var3) {
            log.info("Method[javaToJson] Error converting java object to jsonNode : {}", var3);
        }

        return jsonNode;
    }

    public static String javaToJsonString(Object object) {
        String jsonString = null;

        try {
            jsonString = OBJECT_MAPPER.writeValueAsString(object);
        } catch (Exception var3) {
            log.info("Method[javaToJsonString] Error converting java object to jsonString : {}", var3);
        }

        return jsonString;
    }

    public static <T> T jsonStringToJava(String jsonString, Class<T> tClass) {
        T object = null;

        try {
            object = OBJECT_MAPPER.readValue(jsonString, tClass);
        } catch (Exception var4) {
            log.info("Method[jsonStringToJava] Error converting jsonString to Java Object : {}", var4);
        }

        return object;
    }

    public static String javaListToJsonArray(List<Object> tList) {
        String jsonStr = null;

        try {
            jsonStr = OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(tList);
        } catch (Exception var3) {
        }

        return jsonStr;
    }
}
