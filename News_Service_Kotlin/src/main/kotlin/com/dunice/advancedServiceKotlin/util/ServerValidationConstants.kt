package com.dunice.advancedServiceKotlin.util

interface ServerValidationConstants {
    companion object {
        const val USERNAME_SIZE_NOT_VALID: String = "Username size should be between 3 and 25"

        const val NEWS_DESCRIPTION_SIZE_NOT_VALID: String = "News description size should be between 3 and 130"

        const val NEWS_DESCRIPTION_HAS_TO_BE_PRESENT: String = "News description mustn't be null"

        const val ID_MUST_BE_POSITIVE: String = "ID must be grater than zero"

        const val REQUIRED_INT_PARAM_PAGE_IS_NOT_PRESENT: String = "Parameter page mustn't be null"

        const val REQUIRED_INT_PARAM_PER_PAGE_IS_NOT_PRESENT: String = "Parameter perPage mustn't be null"

        const val USERNAME_HAS_TO_BE_PRESENT: String = "Username mustn't be null"

        const val TAGS_NOT_VALID: String = "Tags invalid"

        const val NEWS_IMAGE_HAS_TO_BE_PRESENT: String = "Image mustn't be null"

        const val USER_WITH_THIS_EMAIL_ALREADY_EXIST: String = "User with this email already exists"

        const val USER_PASSWORD_NOT_VALID: String = "author password should be more than 6 symbols"

        const val USER_AVATAR_NOT_NULL: String = "author avatar mustn't be null"

        const val USER_AVATAR_NOT_VALID: String = "author avatar should be between 3 and 130"

        const val MAX_UPLOAD_SIZE_EXCEEDED: String = "Maximum upload size exceeded"

        const val PER_PAGE_MAX_NOT_VALID: String = "perPage must be less or equal 100"

        const val PER_PAGE_MIN_NOT_VALID: String = "perPage must be greater or equal 1"

        const val PAGE_SIZE_NOT_VALID: String = "news page must be greater or equal 1"

        const val USER_EMAIL_NOT_VALID: String = "author email must be a well-formed email address"

        const val PARAM_PER_PAGE_NOT_NULL: String = "Required Integer parameter 'perPage' is not present"

        const val PARAM_PAGE_NOT_NULL: String = "Required Integer parameter 'page' is not present"

        const val NEWS_TITLE_NOT_NULL: String = "title has to be present"

        const val NEWS_TITLE_SIZE: String = "news title size not valid"

        const val USER_ROLE_NOT_VALID: String = "author role should be between 3 and 25"

        const val USER_PASSWORD_NULL: String = "author password must be null"

        const val USER_EMAIL_NOT_NULL: String = "author email mustn't be null"

        const val ROLE_SIZE_NOT_VALID: String = "role size not valid"

        const val EMAIL_SIZE_NOT_VALID: String = "email size not valid"

        const val TOKEN_NOT_PROVIDED: String = "JWT token not provided"

        const val USER_ID_NULL: String = "User id must be null"

        const val USERNAME_NULL: String = "Username must be null"

        const val USER_ROLE_NULL: String = "User role must be null"

        const val TOKEN_POSITION_MISMATCH: String = "Token must be in authorization header"

        const val NEWS_IMAGE_LENGTH: String = "Image link length should be between 3 and 130"

        const val NEWS_ID_NULL: String = "News id must be null"

        const val PASSWORD_NOT_VALID: String = "password not valid"

        const val USER_NAME_HAS_TO_BE_PRESENT: String = "User name has to be present"

        const val TODO_TEXT_NOT_NULL: String = "todo text not null"

        const val TODO_TEXT_SIZE_NOT_VALID: String = "size not valid"

        const val TODO_STATUS_NOT_NULL: String = "todo status not null"

        const val TASK_NOT_FOUND: String = "task not found"

        const val TASK_PATCH_UPDATED_NOT_CORRECT_COUNT: String = "task patch updated not correct count"

        const val TASKS_PAGE_GREATER_OR_EQUAL_1: String = "task page greater or equal 1"

        const val TASKS_PER_PAGE_GREATER_OR_EQUAL_1: String = "tasks per page greater or equal 1"

        const val TASKS_PER_PAGE_LESS_OR_EQUAL_100: String = "tasks per page less or equal 100"

        const val HTTP_MESSAGE_NOT_READABLE_EXCEPTION: String = "Http request not valid"

        const val NO_RIGHT_TO_CHANGE_NEWS: String = "The current author does not have the right to change the news"
    }
}
