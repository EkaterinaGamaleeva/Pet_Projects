package com.api.serviceYandexMail.util;

public interface AppConstants {

    String STRING_FORMAT_ERROR = "%s  Код ошибки: %d  Сообщение: %s";

    String PATTERN_FORMAT_EMAIL = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+[A-Za-z]{2,6}$";

    String DEFAULT_EMAIL = "e.gamaleeva@tg.dunice.net";

    String SUBJECT_EMAIL = "Екатерина Гамалеева";

    String DEFAULT_TEXT_EMAIL = "Присылаю вам пдф файл, хорошего дня";

    String EMAIL_SEND_OK = "Все прошло хорошо, файл успешно отправлен на почту";

    String CONTENT_TYPE="application/octet-stream";
}
