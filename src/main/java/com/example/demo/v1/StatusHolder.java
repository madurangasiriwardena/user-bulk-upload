package com.example.demo.v1;

import java.util.HashMap;
import java.util.Map;

public class StatusHolder {

    private static final Map<String, Map<String, Integer>> statusMap = new HashMap<>();

    public static void addStatus(String correlationId, String username, Integer status) {

        Map<String, Integer> statusList = statusMap.computeIfAbsent(correlationId, k -> new HashMap<>());
        statusList.put(username, status);
    }

    public static Map<String, Integer> getStatus(String correlationId) {
        return statusMap.get(correlationId);
    }
}
