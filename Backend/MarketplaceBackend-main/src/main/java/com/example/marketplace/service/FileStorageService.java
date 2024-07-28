package com.example.marketplace.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import com.example.marketplace.config.StorageProperties;
import com.example.marketplace.exception.StorageException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private final Path rootLocation;
    private final Path productLocation;
    private final Path accountLocation;
    private final Path categoryLocation;
    private final Path brandLocation;
    private final StorageProperties storageProperties;

    public FileStorageService(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
        this.rootLocation = Paths.get(storageProperties.location());
        this.productLocation=rootLocation.resolve("product");
        this.accountLocation=rootLocation.resolve("account");
        this.categoryLocation=rootLocation.resolve("category");
        this.brandLocation=rootLocation.resolve("brand");
    }

    public void store(MultipartFile file,String subPath) {
        try {
            if (file.isEmpty()) {
                System.out.println("file is empty");
                throw new StorageException("Failed to store empty file.");
            }
            System.out.println("file is present");
            Path destinationFile = this.rootLocation.resolve(subPath)
                    .normalize().toAbsolutePath();
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            System.out.println("failed");
            throw new StorageException("Failed to store file.", e);
        }
    }

    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageException(
                        "Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageException("Could not read file: " + filename, e);
        }
    }
    public void delete(String fileName) throws IOException {
        FileSystemUtils.deleteRecursively(rootLocation.resolve(fileName));
    }


    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rootLocation);
            Files.createDirectories(productLocation);
            Files.createDirectories(accountLocation);
            Files.createDirectories(categoryLocation);
            Files.createDirectories(brandLocation);

        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
