package com.galaxy.simple_crud.utils

import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.time.Instant

@Component
class FileUpload {
    fun storeImage(image: MultipartFile, path: String): String {
        generateFolder(path)

        val fileExtension = image.originalFilename?.substringAfterLast(".") ?: throw IllegalArgumentException("Invalid file name")
        val fileName = "${Instant.now().toEpochMilli()}.$fileExtension"
        val filePath = Paths.get(path.replace("\\", "/"), fileName)

        try {
            Files.copy(image.inputStream, filePath)
        } catch (e: IOException) {
            throw RuntimeException(e.message, e)
        }

        return fileName
    }

    fun updateImage(newImage: MultipartFile, newPath: String, oldImagePath: String): String {
        // Remove old image
        removeImage(oldImagePath)

        // Store new image
        return storeImage(newImage, newPath)
    }

    fun removeImage(imagePath: String) {
        val file = File(imagePath)
        if (file.exists()) {
            if (!file.delete()) {
                throw RuntimeException("Failed to delete image")
            }
        } else {
            throw IllegalArgumentException("Image not found at path: $imagePath")
        }
    }

    fun generateFolder(path: String) {
        val directory = Paths.get(path.replace("\\", "/"))
        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory)
            } catch (e: IOException) {
                throw RuntimeException("Failed to create directory", e)
            }
        }
    }
}