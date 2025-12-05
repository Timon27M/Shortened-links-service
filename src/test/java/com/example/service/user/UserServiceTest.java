package com.example.service.user;

import com.example.model.UrlInfo;
import com.example.model.UserData;
import com.example.repository.UserRepository;
import com.example.utils.ColorPrint;
import com.example.utils.JavaUrlValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JavaUrlValidator javaUrlValidator;

    @InjectMocks
    private UserService userService;

    private final String LOGIN = "testUser";
    private final String ORIGINAL_URL = "https://example.com";
    private final UUID USER_UUID = UUID.randomUUID();

    @Test
    void createUser_WithValidUrl_CreatesUserAndShortUrl() {
        try (MockedStatic<JavaUrlValidator> validatorMock = mockStatic(JavaUrlValidator.class);
             MockedStatic<ColorPrint> colorPrintMock = mockStatic(ColorPrint.class)) {

            // Arrange
            validatorMock.when(() -> JavaUrlValidator.isValidUrl(ORIGINAL_URL)).thenReturn(true);
            when(userRepository.checkUser(LOGIN)).thenReturn(false);

            // Act
            userService.createUser(LOGIN, ORIGINAL_URL, 5, 10);

            // Assert
            verify(userRepository).saveUser(eq(LOGIN), any(UserData.class));
            colorPrintMock.verify(() -> ColorPrint.printlnGreen(contains("Создана короткая ссылка:")));
        }
    }

    @Test
    void createUser_WithExistingUser_ShowsErrorMessage() {
        try (MockedStatic<JavaUrlValidator> validatorMock = mockStatic(JavaUrlValidator.class);
             MockedStatic<ColorPrint> colorPrintMock = mockStatic(ColorPrint.class)) {

            // Arrange
            validatorMock.when(() -> JavaUrlValidator.isValidUrl(ORIGINAL_URL)).thenReturn(true);
            when(userRepository.checkUser(LOGIN)).thenReturn(true);

            // Act
            userService.createUser(LOGIN, ORIGINAL_URL, 5, 10);

            // Assert
            verify(userRepository, never()).saveUser(any(), any());
            colorPrintMock.verify(() -> ColorPrint.printlnRed("Пользователь существует"));
        }
    }

//    @Test
//    void addUrlToUser_WhenUserExists_AddsUrl() {
//        try (MockedStatic<JavaUrlValidator> validatorMock = mockStatic(JavaUrlValidator.class);
//             MockedStatic<ColorPrint> colorPrintMock = mockStatic(ColorPrint.class)) {
//
//            // Arrange
//            validatorMock.when(() -> JavaUrlValidator.isValidUrl(ORIGINAL_URL)).thenReturn(true);
//            when(userRepository.checkUser(LOGIN)).thenReturn(true);
//            UserData userData = mock(UserData.class);
//            when(userRepository.findUser(LOGIN)).thenReturn(userData);
//
//            // Act
//            userService.addUrlToUser(LOGIN, ORIGINAL_URL, 5, 10);
//
//            // Assert
//            verify(userRepository).saveUser(LOGIN, userData);
//            colorPrintMock.verify(() -> ColorPrint.printlnGreen(contains("Создана короткая ссылка:")));
//        }
//    }

    @Test
    void addUrlToUser_WhenUserNotExists_ShowsError() {
        try (MockedStatic<JavaUrlValidator> validatorMock = mockStatic(JavaUrlValidator.class);
             MockedStatic<ColorPrint> colorPrintMock = mockStatic(ColorPrint.class)) {

            // Arrange
            validatorMock.when(() -> JavaUrlValidator.isValidUrl(ORIGINAL_URL)).thenReturn(true);
            when(userRepository.checkUser(LOGIN)).thenReturn(false);

            // Act
            userService.addUrlToUser(LOGIN, ORIGINAL_URL, 5, 10);

            // Assert
            verify(userRepository, never()).saveUser(any(), any());
            colorPrintMock.verify(() -> ColorPrint.printlnRed("Пользователь не найден"));
        }
    }

    @Test
    void getOriginalUrl_WhenUrlExists_ReturnsOriginalUrl() {
        // Arrange
        HashMap<String, UserData> users = new HashMap<>();
        UserData userData = new UserData();
        UrlInfo urlInfo = new UrlInfo(ORIGINAL_URL, 5, 10);
        userData.addUrl("short123", urlInfo);
        users.put(LOGIN, userData);

        when(userRepository.getUsers()).thenReturn(users);

        // Act
        String result = userService.getOriginalUrl("short123");

        // Assert
        assertEquals(ORIGINAL_URL, result);
    }

    @Test
    void getOriginalUrl_WhenUrlNotExists_ReturnsNull() {
        // Arrange
        when(userRepository.getUsers()).thenReturn(new HashMap<>());

        // Act
        String result = userService.getOriginalUrl("nonExistent");

        // Assert
        assertNull(result);
    }

    @Test
    void decrementLimitUrl_WhenUrlExists_DecrementsLimit() {
        // Arrange
        HashMap<String, UserData> users = new HashMap<>();
        UserData userData = mock(UserData.class);
        HashMap<String, UrlInfo> urls = new HashMap<>();
        UrlInfo urlInfo = new UrlInfo(ORIGINAL_URL, 5, 10);
        urls.put("short123", urlInfo);

        when(userData.getUrls()).thenReturn(urls);
        users.put(LOGIN, userData);
        when(userRepository.getUsers()).thenReturn(users);

        // Act
        userService.decrementLimitUrl("short123");

        // Assert
        assertEquals(4, urlInfo.getLimit()); // 5 - 1 = 4
        verify(userRepository).saveUser(LOGIN, userData);
    }

    @Test
    void checkOriginalUrlByUser_WhenUrlExists_ReturnsTrue() {
        // Arrange
        UserData userData = mock(UserData.class);
        HashMap<String, UrlInfo> urls = new HashMap<>();
        urls.put("short123", new UrlInfo(ORIGINAL_URL, 5, 10));

        when(userRepository.findUser(LOGIN)).thenReturn(userData);
        when(userData.getUrls()).thenReturn(urls);

        // Act
        boolean result = userService.checkOriginalUrlByUser(LOGIN, ORIGINAL_URL);

        // Assert
        assertTrue(result);
    }

    @Test
    void checkUser_WhenUserExists_ReturnsTrue() {
        // Arrange
        when(userRepository.checkUser(LOGIN)).thenReturn(true);

        // Act
        boolean result = userService.checkUser(LOGIN);

        // Assert
        assertTrue(result);
    }

    @Test
    void checkUserUUID_WhenUUIDMatches_ReturnsTrue() {
        // Arrange
        UserData userData = mock(UserData.class);
        when(userRepository.findUser(LOGIN)).thenReturn(userData);
        when(userData.getId()).thenReturn(USER_UUID);

        // Act
        boolean result = userService.checkUserUUID(LOGIN, USER_UUID);

        // Assert
        assertTrue(result);
    }
}
