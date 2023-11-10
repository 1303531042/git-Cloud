package com.ruoyi.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.*;

/**
 * @auther: KUN
 * @date: 2023/8/21
 * @description:
 */
public class JSONUtils {

    private static void traverse(JsonNode node, String path, Map<String, JsonNode> map) {
        if (node.isObject()) {
            ObjectNode objectNode = (ObjectNode) node;
            Iterator<Map.Entry<String, JsonNode>> iter = objectNode.fields();

            String newPath = path.isEmpty() ? "" : path + ".";

            while (iter.hasNext()) {
                Map.Entry<String, JsonNode> entry = iter.next();
                traverse(entry.getValue(), newPath + entry.getKey(), map);
            }
        } else if (node.isArray()) {
            ArrayNode arrayNode = (ArrayNode) node;
            for (int i = 0; i < arrayNode.size(); i++) {
                traverse(arrayNode.get(i), path + "[" + i + "]", map);
            }
        } else {
            map.put(path, node);
        }
    }
    public static String getData(String jsonMessage, String jsonExpression) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(jsonMessage);
            Map<String, JsonNode>jsonNodeHashMap = new HashMap<>();
            traverse(jsonNode, "", jsonNodeHashMap);
            JsonNode resultNode = jsonNodeHashMap.get(jsonExpression);
            if (resultNode == null) {
                return null;
            }
            return resultNode.asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
