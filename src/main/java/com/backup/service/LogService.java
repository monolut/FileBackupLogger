package com.backup.service;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogService {

    private static final Path LOG_FILE = Paths.get("logs", "backup.log");

    public LogService() throws IOException {

        Files.createDirectories(LOG_FILE.getParent());

        if (!Files.exists(LOG_FILE)) {
            Files.createFile(LOG_FILE);
        }
    }

    public void writeLog(
            String status,
            Path source,
            Path destination,
            long fileCount,
            long durationMillis,
            boolean zipEnabled
    ) throws IOException {

        String time = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        String log = """
                ==========================================
                Backup Time : %s
                Status      : %s
                Source      : %s
                Destination : %s
                Files       : %d
                Duration    : %d ms
                ZIP         : %s
                ==========================================

                """.formatted(
                time,
                status,
                source,
                destination,
                fileCount,
                durationMillis,
                zipEnabled ? "YES" : "NO"
        );

        Files.writeString(
                LOG_FILE,
                log,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );
    }
}

