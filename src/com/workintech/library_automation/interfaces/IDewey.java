package com.workintech.library_automation.interfaces;

import com.workintech.library_automation.enums.DeweyField;

import java.util.HashMap;
import java.util.Map;

public interface IDewey {


    String generateDeweyRepresentation();

    static String combineDeweyStrings(String... strings) {
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < strings.length - 1; ++i) {
            builder.append(strings[i]).append(',');
        }

        builder.append(strings[strings.length - 1]);
        return builder.toString();
    }

    static String combineFieldValuePairs(String... pairs) {

        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < pairs.length - 1; ++i) {
            builder.append(pairs[i]).append(',');
        }

        builder.append(pairs[pairs.length - 1]);
        return builder.toString();
    }

    static String generateFieldValuePair(DeweyField deweyField, String deweyValue) {
        return String.valueOf(deweyField).toUpperCase() + ':' + deweyValue.replaceAll(" ", "").toUpperCase();
    }

    static String generateValue(String val) {
        return val.replaceAll(" ", "").toUpperCase();
    }

    static Map<String, String> split(String deweyString) {

        Map<String, String> map = new HashMap<>();

        String[] fieldValuePairs = deweyString.split(",");
        for(String fieldValue: fieldValuePairs) {

            String[] arrFieldValue = fieldValue.split(":");
            map.put(arrFieldValue[0], arrFieldValue[1]);
        }

        return map;
    }

}
