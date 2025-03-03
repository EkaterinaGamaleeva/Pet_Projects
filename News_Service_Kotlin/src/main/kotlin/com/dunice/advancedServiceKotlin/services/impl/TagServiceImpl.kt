package com.dunice.advancedServiceKotlin.services.impl

import com.dunice.advancedServiceKotlin.dto.request.NewsRequest
import com.dunice.advancedServiceKotlin.exception.CustomException
import com.dunice.advancedServiceKotlin.models.Tag
import com.dunice.advancedServiceKotlin.repository.TagRepository
import com.dunice.advancedServiceKotlin.services.TagService
import com.dunice.advancedServiceKotlin.util.ServerErrorCodes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

@Service
class TagServiceImpl @Autowired constructor(private val tagRepository: TagRepository) : TagService {
    @Transactional
    override fun parseTag(newsRequest: NewsRequest): Set<Tag> {
        val tagsForNonExistent = mutableSetOf<Tag>()
        val tags = newsRequest.tags ?: throw CustomException(ServerErrorCodes.TASK_NOT_FOUND)

        val tagsSet = tags.map { s: String? ->
            tagRepository.findByTitle(s)?.orElseGet {
                val newTag = Tag(title = s ?: "")
                tagsForNonExistent.add(newTag)
                newTag
            }?: throw CustomException(ServerErrorCodes.TASK_NOT_FOUND)
        }.toSet()

        if (tagsForNonExistent.isNotEmpty()) {
            tagRepository.saveAll(tagsForNonExistent)
        }

        return tagsSet
    }

}
