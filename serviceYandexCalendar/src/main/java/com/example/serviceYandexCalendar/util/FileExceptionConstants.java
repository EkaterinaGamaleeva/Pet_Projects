package com.example.serviceYandexCalendar.util;

public interface FileExceptionConstants {

    String FILE_IS_EMPTY = "you sent an empty file";

    String FILE_LOADING_ERROR = "failed to download file";

    String FILE_MAX_SIZE = "maximum file size 1GB";

    String ERROR_LINKS_UPLOAD_FILE = "сould not get link to upload file to disk";

    String FILE_LOADING_ERROR_BY_DISK = "failed to save file to disk";

    String SERVER_ERROR = "server not connect";

    String FOLDER_NAME_NOT_MATCHER_PATTERN = "pattern --- disk:/folder_name";

    String FILE_NAME_ALREADY_EXIST = "a file with the same name already exists";

    String FAILED_TO_CREATE_AN_MEETING = "failed to create a calendar appointment";
}
