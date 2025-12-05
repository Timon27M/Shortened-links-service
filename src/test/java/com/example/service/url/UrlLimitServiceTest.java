package com.example.service.url;

import static org.junit.jupiter.api.Assertions.*;

import com.example.model.UrlInfo;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

class UrlLimitServiceTest {

	@Test
	void decrementLimit_WhenUrlExists_DecrementsAndReturnsTrue() {
		// Arrange
		HashMap<String, UrlInfo> urls = new HashMap<>();
		UrlInfo urlInfo = new UrlInfo("https://example.com", 5, 10);
		urls.put("abc123", urlInfo);

		// Act
		boolean result = UrlLimitService.decrementLimit("abc123", urls);

		// Assert
		assertTrue(result);
		assertEquals(4, urlInfo.getLimit()); // 5 - 1 = 4
	}

	@Test
	void decrementLimit_WhenUrlNotExists_ReturnsFalse() {
		// Arrange
		HashMap<String, UrlInfo> urls = new HashMap<>();
		urls.put("otherUrl", new UrlInfo("https://example.com", 5, 10));

		// Act
		boolean result = UrlLimitService.decrementLimit("abc123", urls);

		// Assert
		assertFalse(result);
	}

	@Test
	void checkLimitUrl_WhenLimitNotZero_ReturnsTrue() {
		// Arrange
		UrlInfo urlInfo = new UrlInfo("https://example.com", 3, 10);

		// Act
		boolean result = UrlLimitService.checkLimitUrl(urlInfo);

		// Assert
		assertTrue(result);
	}

	@Test
	void checkLimitUrl_WhenLimitIsZero_ReturnsFalse() {
		// Arrange
		UrlInfo urlInfo = new UrlInfo("https://example.com", 0, 10);

		// Act
		boolean result = UrlLimitService.checkLimitUrl(urlInfo);

		// Assert
		assertFalse(result);
	}

	@Test
	void checkLimitUrl_WhenUrlInfoIsNull_ReturnsFalse() {
		// Act
		boolean result = UrlLimitService.checkLimitUrl(null);

		// Assert
		assertFalse(result);
	}

	@Test
	void checkLimitUrl_WhenLimitIsNegative_ReturnsFalse() {
		// Arrange
		UrlInfo urlInfo = new UrlInfo("https://example.com", -1, 10);

		// Act
		boolean result = UrlLimitService.checkLimitUrl(urlInfo);
		System.out.println(result);

		// Assert
		assertFalse(result);
	}
}
