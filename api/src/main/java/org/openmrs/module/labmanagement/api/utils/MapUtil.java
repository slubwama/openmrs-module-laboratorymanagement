package org.openmrs.module.labmanagement.api.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapUtil {

    public static LinkedHashMap<Object, Object> deepMerge(Map<?, ?> leftMap, Map<?, ?> rightMap, int maximumDepth) {
        LinkedHashMap<Object, Object> mergedMap = new LinkedHashMap<>(leftMap);

        for (Map.Entry<?,?> entry : rightMap.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();

            if (mergedMap.containsKey(key)) {
                Object existingValue = mergedMap.get(key);

                if ( maximumDepth > 0 && existingValue instanceof Map && value instanceof Map) {
                    // Recursively merge nested maps
                    mergedMap.put(key, deepMerge((Map) existingValue, (Map) value, maximumDepth - 1));
                } else {
                    // Overwrite with the value from map2
                    mergedMap.put(key, value);
                }
            } else {
                mergedMap.put(key, value);
            }
        }

        return mergedMap;
    }
}
