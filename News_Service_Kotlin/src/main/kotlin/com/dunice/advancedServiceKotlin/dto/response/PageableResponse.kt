package com.dunice.advancedServiceKotlin.dto.response

import java.util.*

class PageableResponse<T> {
    private var content: T? = null

    @JvmField
    var numberOfElements: Int? = null

    constructor(content: T) {
        this.content = content
    }

    fun getContent(): T? {
        return content
    }

    fun setContent(content: T) {
        this.content = content
    }

    constructor(content: T, numberOfElements: Int?) {
        this.content = content
        this.numberOfElements = numberOfElements
    }

    constructor()


    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as PageableResponse<*>
        return content == that.content && numberOfElements == that.numberOfElements
    }

    override fun hashCode(): Int {
        return Objects.hash(content, numberOfElements)
    }
}
