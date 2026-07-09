package com.backup.service;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BackupService {

    private static final String VERSION = "v1.0.0";

    public Path backup(Path source, Path backupDirectory) throws IOException {

        if (!Files.exists(source))
            throw new IOException("Source folder doesn't exist.");

        String time = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

        Path destination = backupDirectory.resolve(
                "backup_" + time + "_" + VERSION);

        Files.createDirectories(destination);

        Files.walkFileTree(source, new SimpleFileVisitor<>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir,
                                                     BasicFileAttributes attrs)
                    throws IOException {

                Path target = destination.resolve(source.relativize(dir));
                Files.createDirectories(target);

                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file,
                                             BasicFileAttributes attrs)
                    throws IOException {

                Files.copy(
                        file,
                        destination.resolve(source.relativize(file)),
                        StandardCopyOption.REPLACE_EXISTING
                );

                return FileVisitResult.CONTINUE;
            }

        });

        return destination;
    }

}