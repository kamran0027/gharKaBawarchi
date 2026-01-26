package com.Kamran.gharKaBawarchi.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;

@Service
public class FileStorageService {
    
    
    private final Path uploadDir;

    //Alowed MIME types

    private static final Set<String> ALLOWED_TYPES=Set.of("image/png", "image/jpeg","image/jpg","image/gif");

    public FileStorageService(@Value("${cook.images.upload-dir}") String uploadDirPath){
        this.uploadDir=Paths.get(uploadDirPath).toAbsolutePath().normalize();
    }

    @PostConstruct
    public void init(){
        try {
            Files.createDirectories(uploadDir);
        } catch (Exception e) {
            throw new RuntimeException("Could not create upload directory : " + uploadDir,e);
        }
    }

    public void validateImage(MultipartFile file, long maxBytes){
        if(file==null || file.isEmpty()){
            throw new IllegalArgumentException("file is empty");
        }
        if (file.getSize()>maxBytes) {
            throw new IllegalArgumentException("File is to Large");
        }
        String cotentType=file.getContentType();

        if (cotentType==null || !ALLOWED_TYPES.contains(cotentType)) {
            throw new IllegalArgumentException("Unsupported image type: "+ cotentType);
        }
    }

    public String storeFile(MultipartFile file){
        try {
            String orignal=StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            String ext="";
            int idx=orignal.lastIndexOf('.');
            if (idx>=0) {
                ext=orignal.substring(idx);
            }

            String filename=UUID.randomUUID().toString()+ext;
            Path target=this.uploadDir.resolve(filename).normalize();

            //copy file
            try(InputStream in=file.getInputStream()){
                Files.copy(in,target,StandardCopyOption.REPLACE_EXISTING);
            }

            return "/uploads/"+filename;
        } catch (IOException e) {

            throw new RuntimeException("failed to store file ",e);
        }
    }

    public boolean deleteFileByWebPath(String webPath){
        if (webPath==null  || webPath.isBlank()) {
            return false;
        }
        String prefix="/uploads";
        String filename=webPath.startsWith(prefix)?webPath.substring(prefix.length()) : Paths.get(webPath).getFileName().toString();
        Path file=this.uploadDir.resolve(filename).normalize();

        try {
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            return false;
        }
    }

    public String updateFile(String existingWebPath, MultipartFile newFile, long maxBytes) {
        // validate new image
        validateImage(newFile, maxBytes);

        if (existingWebPath == null || existingWebPath.isBlank()) {
            throw new IllegalArgumentException("Existing file path is required");
        }

        try {
            String prefix = "/uploads/";
            String filename = existingWebPath.startsWith(prefix)
                ? existingWebPath.substring(prefix.length())
                    : Paths.get(existingWebPath).getFileName().toString();

            Path target = this.uploadDir.resolve(filename).normalize();

            // safety check (prevents path traversal)
            if (!target.startsWith(this.uploadDir)) {
                throw new RuntimeException("Invalid file path");
            }

            // overwrite existing file
            try (InputStream in = newFile.getInputStream()) {
                Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
            }

            // path stays the same
            return existingWebPath;

        } catch (IOException e) {
            throw new RuntimeException("Failed to update file", e);
        }
    }
}   


