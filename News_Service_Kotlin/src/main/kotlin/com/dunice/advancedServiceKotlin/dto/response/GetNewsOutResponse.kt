package com.dunice.advancedServiceKotlin.dto.response

import lombok.*

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
class GetNewsOutResponse {
    private val description: String? = null

    private val id: Long? = null

    private val image: String? = null

    private val tags: Set<TagResponse>? = null

    private val title: String? = null

    private val userId: String? = null

    private val username: String? = null
}
