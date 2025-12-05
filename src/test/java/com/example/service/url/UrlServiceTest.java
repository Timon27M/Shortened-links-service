package com.example.service.url;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.model.UrlInfo;
import com.example.utils.CustomUrlShortener;
import java.util.HashMap;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

	private final UrlService urlService = new UrlService();
	private final String ORIGINAL_URL = "https://example.com";
	private final String SHORT_URL = "abc123";
	private final UUID USER_ID = UUID.randomUUID();

	@Test
	void createShortUrl_ReturnsShortenedUrl() {
		try (MockedStatic<CustomUrlShortener> mock = mockStatic(CustomUrlShortener.class)) {
			// Arrange
			String expectedShort = "short123";
			mock.when(() -> CustomUrlShortener.shortenUrl(ORIGINAL_URL, USER_ID)).thenReturn(expectedShort);

			// Act
			String result = urlService.createShortUrl(ORIGINAL_URL, USER_ID);

			// Assert
			assertEquals(expectedShort, result);
			mock.verify(() -> CustomUrlShortener.shortenUrl(ORIGINAL_URL, USER_ID));
		}
	}

	@Test
	void createShortUrl_WithDifferentUrls_ReturnsDifferentResults() {
		try (MockedStatic<CustomUrlShortener> mock = mockStatic(CustomUrlShortener.class)) {
			// Arrange
			String url1 = "https://site1.com";
			String url2 = "https://site2.com";
			UUID id1 = UUID.randomUUID();
			UUID id2 = UUID.randomUUID();

			mock.when(() -> CustomUrlShortener.shortenUrl(url1, id1)).thenReturn("short1");
			mock.when(() -> CustomUrlShortener.shortenUrl(url2, id2)).thenReturn("short2");

			// Act & Assert
			assertEquals("short1", urlService.createShortUrl(url1, id1));
			assertEquals("short2", urlService.createShortUrl(url2, id2));
		}
	}

	@Test
	void createUrlInfo_WithAllParameters_CreatesCorrectObject() {
		// Act
		UrlInfo urlInfo = urlService.createUrlInfo(ORIGINAL_URL, 11, 12);

		// Assert
		assertEquals(ORIGINAL_URL, urlInfo.getOriginalUrl());
		assertEquals(11, urlInfo.getLimit());
		assertEquals(12, urlInfo.getExpirationTime());
	}

	@Test
	void createUrlInfo_WithNullParameters_UsesDefaults() {
		// Act
		UrlInfo urlInfo = urlService.createUrlInfo(ORIGINAL_URL, null, null);

		// Assert
		assertEquals(ORIGINAL_URL, urlInfo.getOriginalUrl());
		assertEquals(5, urlInfo.getLimit()); // Значение по умолчанию
		assertEquals(10, urlInfo.getExpirationTime()); // Значение по умолчанию
	}

	@Test
	void deleteUrl_WhenUrlExists_ReturnsTrueAndRemoves() {
		// Arrange
		HashMap<String, UrlInfo> map = new HashMap<>();
		map.put(SHORT_URL, new UrlInfo(ORIGINAL_URL, 5, 10));

		// Act
		boolean result = urlService.deleteUrl(SHORT_URL, map);

		// Assert
		assertTrue(result);
		assertFalse(map.containsKey(SHORT_URL));
	}

	@Test
	void deleteUrl_WhenUrlNotExists_ReturnsFalse() {
		// Arrange
		HashMap<String, UrlInfo> map = new HashMap<>();
		map.put("otherUrl", new UrlInfo(ORIGINAL_URL, 5, 10));

		// Act
		boolean result = urlService.deleteUrl(SHORT_URL, map);

		// Assert
		assertFalse(result);
		assertEquals(1, map.size());
	}

	@Test
	void isExistsOriginalUrl_WhenExists_ReturnsTrue() {
		// Arrange
		HashMap<String, UrlInfo> map = new HashMap<>();
		map.put(SHORT_URL, new UrlInfo(ORIGINAL_URL, 5, 10));
		map.put("url2", new UrlInfo("https://other.com", 3, 7));

		// Act
		boolean result = urlService.isExistsOriginalUrl(ORIGINAL_URL, map);

		// Assert
		assertTrue(result);
	}

	@Test
	void isExistsOriginalUrl_WhenNotExists_ReturnsFalse() {
		// Arrange
		HashMap<String, UrlInfo> map = new HashMap<>();
		map.put(SHORT_URL, new UrlInfo("https://different.com", 5, 10));

		// Act
		boolean result = urlService.isExistsOriginalUrl(ORIGINAL_URL, map);

		// Assert
		assertFalse(result);
	}
}
