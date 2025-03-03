package com.dunice.projectNews.controllers;

import com.dunice.projectNews.dto.common.BaseSuccessResponse;
import com.dunice.projectNews.dto.common.CustomSuccessResponse;
import com.dunice.projectNews.dto.request.NewsRequest;
import com.dunice.projectNews.dto.response.GetNewsOutResponse;
import com.dunice.projectNews.dto.response.PageableResponse;
import com.dunice.projectNews.services.NewsService;
import com.dunice.projectNews.util.ServerValidationConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/news")
@Validated
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping
    public ResponseEntity<CustomSuccessResponse<PageableResponse<List<GetNewsOutResponse>>>> getNews(@RequestParam(value = "page")
                                                                                                     @NotNull(message = ServerValidationConstants.PARAM_PAGE_NOT_NULL)
                                                                                                     @Min(value = 1, message = ServerValidationConstants.PAGE_SIZE_NOT_VALID)
                                                                                                     Integer page,
                                                                                                     @RequestParam(value = "perPage")
                                                                                                     @NotNull(message = ServerValidationConstants.PARAM_PER_PAGE_NOT_NULL)
                                                                                                     @Min(value = 1, message = ServerValidationConstants.PER_PAGE_MIN_NOT_VALID)
                                                                                                     @Max(value = 100, message = ServerValidationConstants.TASKS_PER_PAGE_LESS_OR_EQUAL_100)
                                                                                                     Integer perPage) {
        return ResponseEntity
                .ok()
                .body(new CustomSuccessResponse<>(newsService.getNews(page, perPage)));
    }

    @PostMapping
    public ResponseEntity<BaseSuccessResponse> createNews(@RequestBody @Valid NewsRequest newsRequest) {
        BaseSuccessResponse baseSuccessResponse = new BaseSuccessResponse();
        baseSuccessResponse.setId(newsService.createNews(newsRequest));
        return ResponseEntity
                .ok()
                .body(baseSuccessResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseSuccessResponse> putNewsById(@PathVariable("id")
                                                           @Positive(message =
                                                                   ServerValidationConstants.ID_MUST_BE_POSITIVE) Long id,
                                                           @RequestBody @Valid NewsRequest newsRequest) {
        newsService.putNewsById(id, newsRequest);
        return ResponseEntity
                .ok()
                .body(new BaseSuccessResponse());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseSuccessResponse> deleteNewsById(@PathVariable("id")
                                                              @Positive(message =
                                                                      ServerValidationConstants.ID_MUST_BE_POSITIVE) Long id) {
        newsService.deleteNewsById(id);
        return ResponseEntity
                .ok()
                .body(new BaseSuccessResponse());
    }

    @GetMapping("/find")
    public ResponseEntity<CustomSuccessResponse<PageableResponse<List<GetNewsOutResponse>>>> getNewsFind(@RequestParam(value = "page")
                                                                                                         @NotNull(message = ServerValidationConstants.PARAM_PAGE_NOT_NULL)
                                                                                                         @Min(value = 1, message = ServerValidationConstants.PAGE_SIZE_NOT_VALID)
                                                                                                         Integer page,
                                                                                                         @RequestParam(value = "perPage")
                                                                                                         @NotNull(message = ServerValidationConstants.PARAM_PER_PAGE_NOT_NULL)
                                                                                                         @Min(value = 1, message = ServerValidationConstants.PER_PAGE_MIN_NOT_VALID)
                                                                                                         @Max(value = 100, message = ServerValidationConstants.TASKS_PER_PAGE_LESS_OR_EQUAL_100)
                                                                                                         Integer perPage,
                                                                                                         @RequestParam(value = "author", required = false) String author,
                                                                                                         @RequestParam(value = "keywords", required = false) String keywords,
                                                                                                         @RequestParam(value = "tags", required = false) Set<String> tags
    ) {
        return ResponseEntity
                .ok()
                .body(new CustomSuccessResponse<>(
                        newsService
                                .getNewsFind(
                                        page,
                                        perPage,
                                        author,
                                        keywords,
                                        tags)));
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<CustomSuccessResponse<PageableResponse<List<GetNewsOutResponse>>>> getUserNews(@PathVariable("userId")
                                                                                                         @Positive(message =
                                                                                                                 ServerValidationConstants.ID_MUST_BE_POSITIVE) UUID id,
                                                                                                         @RequestParam(value = "page")
                                                                                                         @NotNull(message = ServerValidationConstants.PARAM_PAGE_NOT_NULL)
                                                                                                         @Min(value = 1, message = ServerValidationConstants.PAGE_SIZE_NOT_VALID)
                                                                                                         Integer page,
                                                                                                         @RequestParam(value = "perPage")
                                                                                                         @NotNull(message = ServerValidationConstants.PARAM_PER_PAGE_NOT_NULL)
                                                                                                         @Min(value = 1, message = ServerValidationConstants.PER_PAGE_MIN_NOT_VALID)
                                                                                                         @Max(value = 100, message = ServerValidationConstants.TASKS_PER_PAGE_LESS_OR_EQUAL_100)
                                                                                                         Integer perPage) {
        return ResponseEntity
                .ok()
                .body(new CustomSuccessResponse<>(newsService
                        .getUserNews(
                                id,
                                page,
                                perPage)));
    }
}
