package com.dunice.advancedServiceKotlin.dto.response

class TagResponse {
    @JvmField
    var tagId: Long? = null

    @JvmField
    var title: String? = null

    constructor(tagId: Long?, title: String?) {
        this.tagId = tagId
        this.title = title
    }

    constructor()
}
