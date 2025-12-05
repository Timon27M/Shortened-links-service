package com.example.service.url;

import com.example.model.UrlInfo;
import com.example.utils.ColorPrint;

public class UrlValidationService {
  public static boolean isValidUrl(UrlInfo urlInfo) {
    if (!UrlExpirationTimeService.checkActiveUrlExpirationTime(urlInfo)) {
      ColorPrint.printlnRed("Ссылка недоступна: Срок жизни ссылки истек, ссылка удалена");
      return false;
    }
    if (!UrlLimitService.checkLimitUrl(urlInfo)) {
      ColorPrint.printlnRed("Ссылка недоступна: Лимит переходов исчерпан.");
      return false;
    }

    return true;
  }
}
