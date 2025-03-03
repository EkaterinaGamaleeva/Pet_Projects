package org.serviceexcel.file_service.util;

public interface AppConstants {
    String SPACE = " ";

    String NO_SPACE = "";

    String DOT = ".";

    int ZERO = 0;

    int ONE = 1;

    String EXTENSION_FILE_PDF = ".pdf";

    String STRING_FORMAT_ERROR = "%s  Код ошибки: %d  Сообщение: %s";

    String PATTERN_FORMAT_EMAIL = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+[A-Za-z]{2,6}$";

    String DEFAULT_EMAIL = "e.gamaleeva@tg.dunice.net";

    String DEFAULT_TEXT_EMAIL = "Присылаю вам пдф файл, хорошего дня";

    String SEND_YANDEX_CALENDAR_OK = "все отлично файл находится на яндекс диске и назначена встреча";

    String EXCHANGE_NAME = "fileExchange";

    String ROUTING_KEY = "fileRoutingKey";

    String QUEUE_NAME = "fileQueue";

    String LOCALHOST = "localhost";

    String USERNAME_RABBIT = "guest";

    String PASSWORD_RABBIT = USERNAME_RABBIT;
}
