package com.api.AdvancedServiceWebFlux.util;

public interface AppConstants {
    String SPACE = " ";

    String NO_SPACE = "";

    String DOT = ".";

    String HEADER_AUTHORIZATION = "Authorization";

    String TOKEN_BEARER = "Bearer" + SPACE;

    String TYPE_APPLICATION_JSON = "application/json";

    String ERRORS = "errorMessage";

    String MESSAGE_STATUS_OK = "Все окей";

    String NO_AUTHORIZATION = "Не авторизованный пользователь";

    String PATTERN_FORMAT_EMAIL = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+[A-Za-z]{2,6}$";

    String PATTERN_FORMAT_UUID = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

    String CODES = "codes";
}
