package com.dunice.advancedServiceKotlin.util

enum class ServerErrorCodes(private val statusCode: Int, @JvmField val message: String) {
    UNKNOWN(0, "unknown"),

    USERNAME_SIZE_NOT_VALID(1, ServerValidationConstants.USERNAME_SIZE_NOT_VALID),

    ROLE_SIZE_NOT_VALID(2, ServerValidationConstants.ROLE_SIZE_NOT_VALID),

    EMAIL_SIZE_NOT_VALID(3, "email size not valid"),

    MUST_NOT_BE_NULL(4, "must not be null"),

    USER_NOT_FOUND(5, ServerValidationConstants.USERNAME_HAS_TO_BE_PRESENT),

    TOKEN_NOT_PROVIDED(6, "JWT token not provided"),

    UNAUTHORISED(7, "unauthorised"),

    USER_EMAIL_NOT_NULL(8, "author email mustn't be null"),

    USER_PASSWORD_NOT_VALID(9, "author password must be more than 6 symbols"),

    USER_ROLE_NOT_NULL(10, ServerValidationConstants.USER_ROLE_NULL),

    NEWS_DESCRIPTION_SIZE(11, ServerValidationConstants.NEWS_DESCRIPTION_SIZE_NOT_VALID),

    NEWS_DESCRIPTION_NOT_NULL(12, ServerValidationConstants.NEWS_DESCRIPTION_HAS_TO_BE_PRESENT),

    NEWS_TITLE_SIZE(13, "news title size not valid"),

    NEWS_TITLE_NOT_NULL(14, "title has to be present"),

    PARAM_PAGE_NOT_NULL(15, "Required Integer parameter 'page' is not present"),

    PARAM_PER_PAGE_NOT_NULL(16, "Required Integer parameter 'perPage' is not present"),

    USER_EMAIL_NOT_VALID(17, ServerValidationConstants.USER_EMAIL_NOT_VALID),

    PAGE_SIZE_NOT_VALID(18, "news page must be greater or equal 1"),

    PER_PAGE_MIN_NOT_VALID(19, "perPage must be greater or equal 1"),

    PER_PAGE_MAX_NOT_VALID(19, "perPage must be less or equal 100"),

    CODE_NOT_NULL(20, "Required String parameter 'code' is not present"),

    EXCEPTION_HANDLER_NOT_PROVIDED(21, "Exception handler not provided"),

    REQUEST_IS_NOT_MULTIPART(22, "Current request is not a multipart request"),

    MAX_UPLOAD_SIZE_EXCEEDED(23, "Maximum upload size exceeded"),

    USER_AVATAR_NOT_NULL(24, "author avatar mustn't be null"),

    PASSWORD_NOT_VALID(25, "password not valid"),

    PASSWORD_NOT_NULL(26, "author password mustn't be null"),

    NEWS_NOT_FOUND(27, "news not found"),

    ID_MUST_BE_POSITIVE(29, ServerValidationConstants.ID_MUST_BE_POSITIVE),

    USER_ALREADY_EXISTS(30, ServerValidationConstants.USER_WITH_THIS_EMAIL_ALREADY_EXIST),

    TODO_TEXT_NOT_NULL(31, ServerValidationConstants.TODO_TEXT_NOT_NULL),

    TODO_TEXT_SIZE_NOT_VALID(32, ServerValidationConstants.TODO_TEXT_SIZE_NOT_VALID),

    TODO_STATUS_NOT_NULL(33, ServerValidationConstants.TODO_STATUS_NOT_NULL),

    TASK_NOT_FOUND(34, ServerValidationConstants.TASK_NOT_FOUND),

    TASK_PATCH_UPDATED_NOT_CORRECT_COUNT(35, ServerValidationConstants.TASK_PATCH_UPDATED_NOT_CORRECT_COUNT),

    TASKS_PAGE_GREATER_OR_EQUAL_1(37, ServerValidationConstants.TASKS_PAGE_GREATER_OR_EQUAL_1),

    TASKS_PER_PAGE_GREATER_OR_EQUAL_1(38, ServerValidationConstants.TASKS_PER_PAGE_GREATER_OR_EQUAL_1),

    TASKS_PER_PAGE_LESS_OR_EQUAL_100(39, ServerValidationConstants.TASKS_PER_PAGE_LESS_OR_EQUAL_100),

    REQUIRED_INT_PARAM_PAGE_IS_NOT_PRESENT(40, ServerValidationConstants.REQUIRED_INT_PARAM_PAGE_IS_NOT_PRESENT),

    REQUIRED_INT_PARAM_PER_PAGE_IS_NOT_PRESENT(
        41,
        ServerValidationConstants.REQUIRED_INT_PARAM_PER_PAGE_IS_NOT_PRESENT
    ),

    USER_NAME_HAS_TO_BE_PRESENT(43, ServerValidationConstants.USER_NAME_HAS_TO_BE_PRESENT),

    TAGS_NOT_VALID(44, ServerValidationConstants.TAGS_NOT_VALID),

    NEWS_IMAGE_HAS_TO_BE_PRESENT(45, ServerValidationConstants.NEWS_IMAGE_HAS_TO_BE_PRESENT),

    USER_WITH_THIS_EMAIL_ALREADY_EXIST(46, "USER_WITH_THIS_EMAIL_ALREADY_EXIST"),

    HTTP_MESSAGE_NOT_READABLE_EXCEPTION(47, ServerValidationConstants.HTTP_MESSAGE_NOT_READABLE_EXCEPTION),

    NO_RIGHT_TO_CHANGE_NEWS(48, ServerValidationConstants.NO_RIGHT_TO_CHANGE_NEWS);

    companion object {
        @JvmField
        val map: MutableMap<String, Int> = HashMap()

        init {
            for (c in entries) {
                map[c.message] = c.statusCode
            }
        }
    }
}
