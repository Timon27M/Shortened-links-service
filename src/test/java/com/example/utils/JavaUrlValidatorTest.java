package com.example.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JavaUrlValidatorTest {
    @Test
    void testValidUrl() {
        JavaUrlValidator validator = new JavaUrlValidator();
        assertTrue(validator.isValidUrl("https://example.com"));
    }
}
