package com.example.utils;

import com.example.model.UserData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class HashMapJsonConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
    // UsersDB → JSON файл
    public static void saveHashMapToJson(HashMap<String, UserData> map, String filePath) {
        try {
            objectMapper.writeValue(new File(filePath), map);
            System.out.println("HashMap успешно сохранен в JSON файл");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // JSON файл → HashMap
    public static HashMap<String, UserData> loadHashMapFromJson(String filePath) {

        try {
            File file = new File(filePath);
            if (!file.exists() || file.length() == 0) {
                System.out.println("⚠️ Файл не существует, возвращаем пустой HashMap");
                return new HashMap<>();
            }

            return objectMapper.readValue(
                    file,
                    new TypeReference<HashMap<String, UserData>>() {}
            );
        } catch (Exception e) {
            System.err.println("❌ Ошибка при загрузке HashMap: " + e.getMessage());
            e.printStackTrace();
            return new HashMap<>();
        }
    }
}
