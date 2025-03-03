package com.example.serviceYandexCalendar.util;

public interface AppConstant {
    String TOKEN_HEADER = "OAuth ";

    String AUTHORIZED_HEAD = "Authorization";

    String BASIC_HEADER = "Basic ";

    String PATH = "path";

    String COLON = ":";

    String TIME_ZONE = "Europe/Moscow";

    String PROD_ID = "-//Events Calendar//iCal4j 1.0//EN";

    String PATTERN_FOLDER = "^disk:/[a-zA-Zа-яА-Я0-9._-]+$";

    String DEFAULT_FOLDER = "disk:/folder";

    String STRING_FORMAT_ERROR = "%s  Код ошибки: %d  Сообщение: %s";

    String PATTERN_INDEXOF = "\"href\":\"";

    String BACKSLASH = "\"";

    String SLASH = "/";

    String FILE_SEND_DISK_OK = "Все прошло хорошо, файл успешно отправлен на диск" + " ,файл находитсяв папке: ";

    String EVENT_SEND_OK = " Встреча назначена ";

    String START = " начало: ";

    String END = " конец: ";

    String DESCRIPTION = " прикрепленные файлы: ";
}
