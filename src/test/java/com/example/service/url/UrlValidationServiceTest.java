package com.example.service.url;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.model.UrlInfo;
import com.example.utils.ColorPrint;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

class UrlValidationServiceTest {

	@Test
	void isValidUrl_WhenAllConditionsValid_ReturnsTrue() {
		try (MockedStatic<UrlExpirationTimeService> expirationMock = mockStatic(UrlExpirationTimeService.class);
				MockedStatic<UrlLimitService> limitMock = mockStatic(UrlLimitService.class);
				MockedStatic<ColorPrint> colorPrintMock = mockStatic(ColorPrint.class)) {

			// Arrange
			UrlInfo urlInfo = mock(UrlInfo.class);
			expirationMock.when(() -> UrlExpirationTimeService.checkActiveUrlExpirationTime(urlInfo)).thenReturn(true);
			limitMock.when(() -> UrlLimitService.checkLimitUrl(urlInfo)).thenReturn(true);

			// Act
			boolean result = UrlValidationService.isValidUrl(urlInfo);

			// Assert
			assertTrue(result);
			colorPrintMock.verifyNoInteractions();
		}
	}

	@Test
	void isValidUrl_WhenExpired_ReturnsFalseAndPrintsMessage() {
		try (MockedStatic<UrlExpirationTimeService> expirationMock = mockStatic(UrlExpirationTimeService.class);
				MockedStatic<UrlLimitService> limitMock = mockStatic(UrlLimitService.class);
				MockedStatic<ColorPrint> colorPrintMock = mockStatic(ColorPrint.class)) {

			// Arrange
			UrlInfo urlInfo = mock(UrlInfo.class);
			expirationMock.when(() -> UrlExpirationTimeService.checkActiveUrlExpirationTime(urlInfo)).thenReturn(false);

			// Act
			boolean result = UrlValidationService.isValidUrl(urlInfo);

			// Assert
			assertFalse(result);
			colorPrintMock
					.verify(() -> ColorPrint.printlnRed("Ссылка недоступна: Срок жизни ссылки истек, ссылка удалена"));
			limitMock.verifyNoInteractions();
		}
	}

	@Test
	void isValidUrl_WhenLimitExceeded_ReturnsFalseAndPrintsMessage() {
		try (MockedStatic<UrlExpirationTimeService> expirationMock = mockStatic(UrlExpirationTimeService.class);
				MockedStatic<UrlLimitService> limitMock = mockStatic(UrlLimitService.class);
				MockedStatic<ColorPrint> colorPrintMock = mockStatic(ColorPrint.class)) {

			// Arrange
			UrlInfo urlInfo = mock(UrlInfo.class);
			expirationMock.when(() -> UrlExpirationTimeService.checkActiveUrlExpirationTime(urlInfo)).thenReturn(true);
			limitMock.when(() -> UrlLimitService.checkLimitUrl(urlInfo)).thenReturn(false);

			// Act
			boolean result = UrlValidationService.isValidUrl(urlInfo);

			// Assert
			assertFalse(result);
			colorPrintMock.verify(() -> ColorPrint.printlnRed("Ссылка недоступна: Лимит переходов исчерпан."));
		}
	}
}
