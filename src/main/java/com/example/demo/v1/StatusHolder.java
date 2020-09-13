package com.example.demo.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatusHolder {

    private static final Map<String, List<BulkResponse>> statusMap = new HashMap<>();

    public static void addStatus(String correlationId, BulkResponse status) {

        List<BulkResponse> statusList = statusMap.computeIfAbsent(correlationId, k -> new ArrayList<>());
        statusList.add(status);
    }

    public static List<BulkResponse> getStatus(String correlationId) {
        return statusMap.get(correlationId);
    }
}
