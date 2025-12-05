package com.example.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JavaUrlValidatorTest {
  @Test
  void testValidUrl() {
    JavaUrlValidator validator = new JavaUrlValidator();
    assertTrue(validator.isValidUrl("https://example.com"));
  }
}
