package com.example.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.util.HashMap;

public class HashMapJsonConverter {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  static {
    objectMapper.findAndRegisterModules();
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
  }

  public static <K, V> void saveHashMapToJson(HashMap<K, V> map, String filePath) {
    try {
      objectMapper.writeValue(new File(filePath), map);
        ColorPrint.printlnGreen("Данные успешно сохранены");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static <K, V> HashMap<K, V> loadHashMapFromJson(
      String filePath, Class<K> keyType, Class<V> valueType) {

    try {
      File file = new File(filePath);
      if (!file.exists() || file.length() == 0) {
        return new HashMap<>();
      }
      JavaType type =
          objectMapper.getTypeFactory().constructMapType(HashMap.class, keyType, valueType);
      return objectMapper.readValue(file, type);
    } catch (Exception e) {
      System.err.println("❌ Ошибка при загрузке HashMap: " + e.getMessage());
      e.printStackTrace();
      return new HashMap<>();
    }
  }
}
