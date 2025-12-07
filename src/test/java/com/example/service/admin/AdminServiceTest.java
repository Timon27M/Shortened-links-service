package com.example.service.admin;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.model.UrlInfo;
import com.example.model.UserData;
import com.example.repository.UserRepository;
import com.example.utils.ColorPrint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

  @Mock private UserRepository userRepository;

  @Mock private UserData userData;

  @Mock private UrlInfo urlInfo;

  @InjectMocks private AdminService adminService;

  private final String TEST_LOGIN = "testUser";
  private final String TEST_SHORT_URL = "abc123";
  private final String NON_EXISTENT_LOGIN = "nonExistentUser";
  private final String NON_EXISTENT_URL = "nonExistentUrl";

  @Test
  void testUpdateUrlLimit_Success() {
    // Arrange
    Integer newLimit = 100;
    when(userRepository.checkUser(TEST_LOGIN)).thenReturn(true);
    when(userRepository.findUser(TEST_LOGIN)).thenReturn(userData);
    when(userData.checkUserShortUrl(TEST_SHORT_URL)).thenReturn(true);
    when(userData.getShortUrlData(TEST_SHORT_URL)).thenReturn(urlInfo);

    // Act
    adminService.updateUrlLimit(TEST_LOGIN, TEST_SHORT_URL, newLimit);

    // Assert
    verify(urlInfo).setLimit(newLimit);
    verify(userRepository).saveUser(TEST_LOGIN, userData);
    verify(urlInfo, never()).setExpirationTime(anyInt());
  }

  @Test
  void testUpdateUrlLimit_UserNotFound() {
    // Arrange
    try (MockedStatic<ColorPrint> colorPrintMock = mockStatic(ColorPrint.class)) {
      when(userRepository.checkUser(NON_EXISTENT_LOGIN)).thenReturn(false);

      // Act
      adminService.updateUrlLimit(NON_EXISTENT_LOGIN, TEST_SHORT_URL, 100);

      // Assert
      colorPrintMock.verify(() -> ColorPrint.printlnRed("Пользователя не существует!"));
      verify(userRepository, never()).findUser(anyString());
      verify(userRepository, never()).saveUser(anyString(), any(UserData.class));
    }
  }

  @Test
  void testUpdateUrlLimit_UrlNotFound() {
    // Arrange
    try (MockedStatic<ColorPrint> colorPrintMock = mockStatic(ColorPrint.class)) {
      when(userRepository.checkUser(TEST_LOGIN)).thenReturn(true);
      when(userRepository.findUser(TEST_LOGIN)).thenReturn(userData);
      when(userData.checkUserShortUrl(NON_EXISTENT_URL)).thenReturn(false);

      // Act
      adminService.updateUrlLimit(TEST_LOGIN, NON_EXISTENT_URL, 100);

      // Assert
      colorPrintMock.verify(() -> ColorPrint.printlnRed("Ссылки не существует!"));
      verify(userData, never()).getShortUrlData(anyString());
      verify(userRepository, never()).saveUser(anyString(), any(UserData.class));
    }
  }

  @Test
  void testUpdateUrlExpirationTime_Success() {
    // Arrange
    Integer newExpirationTime = 30;
    when(userRepository.checkUser(TEST_LOGIN)).thenReturn(true);
    when(userRepository.findUser(TEST_LOGIN)).thenReturn(userData);
    when(userData.checkUserShortUrl(TEST_SHORT_URL)).thenReturn(true);
    when(userData.getShortUrlData(TEST_SHORT_URL)).thenReturn(urlInfo);

    // Act
    adminService.updateUrlExpirationTime(TEST_LOGIN, TEST_SHORT_URL, newExpirationTime);

    // Assert
    verify(urlInfo).setExpirationTime(newExpirationTime);
    verify(userRepository).saveUser(TEST_LOGIN, userData);
    verify(urlInfo, never()).setLimit(anyInt());
  }

  @Test
  void testDeleteUserUrl_Success() {
    // Arrange
    when(userRepository.checkUser(TEST_LOGIN)).thenReturn(true);
    when(userRepository.findUser(TEST_LOGIN)).thenReturn(userData);
    when(userData.checkUserShortUrl(TEST_SHORT_URL)).thenReturn(true);

    // Act
    adminService.deleteUserUrl(TEST_LOGIN, TEST_SHORT_URL);

    // Assert
    verify(userRepository).deleteUrlToUser(TEST_LOGIN, TEST_SHORT_URL);
    verify(userRepository, never()).deleteUser(anyString());
  }

  @Test
  void testDeleteUserUrl_UserNotFound() {
    // Arrange
    try (MockedStatic<ColorPrint> colorPrintMock = mockStatic(ColorPrint.class)) {
      when(userRepository.checkUser(NON_EXISTENT_LOGIN)).thenReturn(false);

      // Act
      adminService.deleteUserUrl(NON_EXISTENT_LOGIN, TEST_SHORT_URL);

      // Assert
      colorPrintMock.verify(() -> ColorPrint.printlnRed("Пользователя не существует!"));
      verify(userRepository, never()).deleteUrlToUser(anyString(), anyString());
    }
  }

  @Test
  void testDeleteUser_Success() {
    // Arrange
    when(userRepository.checkUser(TEST_LOGIN)).thenReturn(true);

    // Act
    adminService.deleteUser(TEST_LOGIN);

    // Assert
    verify(userRepository).deleteUser(TEST_LOGIN);
    verify(userRepository, never()).deleteUrlToUser(anyString(), anyString());
  }

  @Test
  void testDeleteUser_UserNotFound() {
    // Arrange
    when(userRepository.checkUser(NON_EXISTENT_LOGIN)).thenReturn(false);

    // Act
    adminService.deleteUser(NON_EXISTENT_LOGIN);

    // Assert
    verify(userRepository, never()).deleteUser(anyString());
    // Не должно быть исключений или сообщений об ошибке (согласно коду)
  }

  @Test
  void testGetUserUrlInfo_Success() {
    // Arrange
    when(userRepository.findUser(TEST_LOGIN)).thenReturn(userData);
    when(userData.checkUserShortUrl(TEST_SHORT_URL)).thenReturn(true);
    when(userData.getShortUrlData(TEST_SHORT_URL)).thenReturn(urlInfo);

    // Act
    UrlInfo result = adminService.getUserUrlInfo(TEST_LOGIN, TEST_SHORT_URL);

    // Assert
    assertNotNull(result);
    assertEquals(urlInfo, result);
    verify(userRepository).findUser(TEST_LOGIN);
    verify(userData).getShortUrlData(TEST_SHORT_URL);
  }

  @Test
  void testGetUserUrlInfo_UserNotFound() {
    // Arrange
    when(userRepository.findUser(NON_EXISTENT_LOGIN)).thenReturn(null);

    // Act
    UrlInfo result = adminService.getUserUrlInfo(NON_EXISTENT_LOGIN, TEST_SHORT_URL);

    // Assert
    assertNull(result);
    verify(userRepository).findUser(NON_EXISTENT_LOGIN);
    verify(userData, never()).checkUserShortUrl(anyString());
  }

  @Test
  void testGetUserUrlInfo_UrlNotFound() {
    // Arrange
    try (MockedStatic<ColorPrint> colorPrintMock = mockStatic(ColorPrint.class)) {
      when(userRepository.findUser(TEST_LOGIN)).thenReturn(userData);
      when(userData.checkUserShortUrl(NON_EXISTENT_URL)).thenReturn(false);

      // Act
      UrlInfo result = adminService.getUserUrlInfo(TEST_LOGIN, NON_EXISTENT_URL);

      // Assert
      assertNull(result);
      colorPrintMock.verify(() -> ColorPrint.printlnRed("Ссылки не существует."));
      verify(userData, never()).getShortUrlData(anyString());
    }
  }

  @Test
  void testGetUserUrlInfo_UserDataNull() {
    // Arrange
    when(userRepository.findUser(TEST_LOGIN)).thenReturn(null);

    // Act
    UrlInfo result = adminService.getUserUrlInfo(TEST_LOGIN, TEST_SHORT_URL);

    // Assert
    assertNull(result);
    verify(userRepository).findUser(TEST_LOGIN);
    verify(userData, never()).checkUserShortUrl(anyString());
  }

  @Test
  void testUpdateUrlLimit_NullLimit() {
    // Arrange
    when(userRepository.checkUser(TEST_LOGIN)).thenReturn(true);
    when(userRepository.findUser(TEST_LOGIN)).thenReturn(userData);

    // Act
    adminService.updateUrlLimit(TEST_LOGIN, TEST_SHORT_URL, null);

    // Assert
    verify(urlInfo, never()).setLimit(null);
    verify(userRepository, never()).saveUser(TEST_LOGIN, userData);
  }

  @Test
  void testUpdateUrlExpirationTime_NullExpirationTime() {
    // Arrange
    when(userRepository.checkUser(TEST_LOGIN)).thenReturn(true);
    when(userRepository.findUser(TEST_LOGIN)).thenReturn(userData);

    // Act
    adminService.updateUrlExpirationTime(TEST_LOGIN, TEST_SHORT_URL, null);

    // Assert
    verify(urlInfo, never()).setExpirationTime(null);
    verify(userRepository, never()).saveUser(TEST_LOGIN, userData);
  }
}
