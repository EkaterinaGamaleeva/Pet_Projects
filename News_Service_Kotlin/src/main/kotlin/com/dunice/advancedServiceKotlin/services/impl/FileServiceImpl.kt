package com.dunice.advancedServiceKotlin.services.impl

import com.dunice.advancedServiceKotlin.exception.CustomException
import com.dunice.advancedServiceKotlin.services.FileService
import com.dunice.advancedServiceKotlin.util.AppConstants
import com.dunice.advancedServiceKotlin.util.ServerErrorCodes
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.net.MalformedURLException
import java.nio.file.Paths
import java.util.*

@Service
class FileServiceImpl : FileService {
    @Value("\${uploadDir}")
    private val UPLOAD_DIR: String? = null

    @Value("\${sitePath}")
    private val SITE_PATH: String? = null

    override fun uploadFile(file: MultipartFile): String {
        if (file.isEmpty) {
            throw CustomException(ServerErrorCodes.EXCEPTION_HANDLER_NOT_PROVIDED)
        }
        try {
            val directoryForUploadFile = File(UPLOAD_DIR)
            if (!directoryForUploadFile.exists()) {
                directoryForUploadFile.mkdir()
            }
            val fileExtension = getFileExtension(file.originalFilename)
            val uuidFileName = UUID.randomUUID().toString() + fileExtension
            val path = Paths.get(UPLOAD_DIR + File.separator + uuidFileName)
            file.transferTo(path)
            return SITE_PATH + uuidFileName
        } catch (e: IOException) {
            throw CustomException(ServerErrorCodes.UNKNOWN)
        }
    }

    override fun giveFile(fileName: String?): UrlResource {
        try {
            val filePath = Paths.get(UPLOAD_DIR + File.separator).resolve(fileName).normalize()
            val resource = UrlResource(filePath.toUri())
            if (resource.exists() && resource.isReadable) {
                return resource
            } else {
                throw CustomException(ServerErrorCodes.EXCEPTION_HANDLER_NOT_PROVIDED)
            }
        } catch (e: MalformedURLException) {
            throw CustomException(ServerErrorCodes.UNKNOWN)
        }
    }

    private fun getFileExtension(originalFileName: String?): String {
        if (originalFileName == null || originalFileName.isEmpty()) {
            return AppConstants.NO_SPACE
        }
        val dotIndex = originalFileName.lastIndexOf(AppConstants.DOT)
        if (dotIndex > 0 && dotIndex < originalFileName.length - 1) {
            return originalFileName.substring(dotIndex)
        }
        return AppConstants.NO_SPACE
    }
}
