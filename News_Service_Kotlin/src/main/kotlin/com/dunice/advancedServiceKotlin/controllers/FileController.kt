package com.dunice.advancedServiceKotlin.controllers

import com.dunice.advancedServiceKotlin.dto.common.CustomSuccessResponse
import com.dunice.advancedServiceKotlin.services.FileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.UrlResource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/file")
class FileController @Autowired constructor(private val fileService: FileService) {
    @PostMapping("/uploadFile")
    fun uploadFile(@RequestParam(value = "file") file: MultipartFile?): ResponseEntity<CustomSuccessResponse<String>> {
        return ResponseEntity
            .ok()
            .body(CustomSuccessResponse(fileService.uploadFile(file!!)))
    }

    @GetMapping("{fileName}")
    fun getFile(
        @PathVariable(value = "fileName") fileName: String?
    ): ResponseEntity<UrlResource> {
        return ResponseEntity
            .ok()
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(fileService.giveFile(fileName))
    }
}
