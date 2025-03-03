package com.dunice.projectNews.dto.request;

import com.dunice.projectNews.util.ServerValidationConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
public class NewsRequest {

    @NotBlank(message = ServerValidationConstants.NEWS_DESCRIPTION_HAS_TO_BE_PRESENT)
    @Size(min = 3, max = 160, message = ServerValidationConstants.NEWS_DESCRIPTION_SIZE_NOT_VALID)
    private String description;

    @NotBlank(message = ServerValidationConstants.NEWS_IMAGE_HAS_TO_BE_PRESENT)
    @Size(min = 3, max = 130, message = ServerValidationConstants.NEWS_IMAGE_HAS_TO_BE_PRESENT)
    private String image;

    private Set<@NotBlank(message = ServerValidationConstants.TAGS_NOT_VALID) String> tags;

    @NotBlank(message = ServerValidationConstants.NEWS_TITLE_NOT_NULL)
    @Size(min = 3, max = 160, message = ServerValidationConstants.NEWS_TITLE_SIZE)
    private String title;
}
