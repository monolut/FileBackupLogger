package com.backup.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipService {

    public void zipFolder(Path sourceFolder, Path zipFile) throws IOException {

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile.toFile()))) {

            Files.walk(sourceFolder).forEach(path -> {

                try {

                    String entryName = sourceFolder
                            .relativize(path)
                            .toString()
                            .replace("\\", "/");

                    if (Files.isDirectory(path)) {

                        // Добавляем пустые папки
                        if (!entryName.isEmpty()) {
                            zos.putNextEntry(new ZipEntry(entryName + "/"));
                            zos.closeEntry();
                        }

                        return;
                    }

                    zos.putNextEntry(new ZipEntry(entryName));

                    Files.copy(path, zos);

                    zos.closeEntry();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });

        }

    }

}

