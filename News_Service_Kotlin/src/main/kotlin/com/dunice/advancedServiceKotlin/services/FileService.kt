package com.dunice.advancedServiceKotlin.services

import org.springframework.core.io.UrlResource
import org.springframework.web.multipart.MultipartFile

interface FileService {
    fun uploadFile(file: MultipartFile): String

    fun giveFile(fileName: String?): UrlResource
}
