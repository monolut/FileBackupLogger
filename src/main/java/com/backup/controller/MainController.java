package com.backup.controller;

import com.backup.service.BackupService;
import com.backup.service.LogService;
import com.backup.service.ZipService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainController {

    @FXML
    private TextField sourceField;

    @FXML
    private TextField destinationField;

    @FXML
    private CheckBox zipCheck;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label statusLabel;

    @FXML
    private TextArea logArea;

    @FXML
    private void browseSource() {

        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select Source Folder");

        File folder = chooser.showDialog(new Stage());

        if (folder != null) {
            sourceField.setText(folder.getAbsolutePath());
        }
    }

    @FXML
    private void browseDestination() {

        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select Destination Folder");

        File folder = chooser.showDialog(new Stage());

        if (folder != null) {
            destinationField.setText(folder.getAbsolutePath());
        }
    }

    @FXML
    private void startBackup() {

        if (sourceField.getText().isBlank()) {
            statusLabel.setText("Please select a source folder.");
            return;
        }

        if (destinationField.getText().isBlank()) {
            statusLabel.setText("Please select a destination folder.");
            return;
        }

        try {

            progressBar.setProgress(0);

            long startTime = System.currentTimeMillis();

            Path source = Paths.get(sourceField.getText());
            Path destination = Paths.get(destinationField.getText());

            BackupService backupService = new BackupService();

            statusLabel.setText("Creating backup...");
            logArea.appendText("Starting backup...\n");

            Path backupFolder = backupService.backup(source, destination);

            progressBar.setProgress(0.6);

            if (zipCheck.isSelected()) {

                statusLabel.setText("Creating ZIP archive...");

                ZipService zipService = new ZipService();

                Path zipFile = backupFolder.getParent().resolve(
                        backupFolder.getFileName().toString() + ".zip"
                );

                zipService.zipFolder(backupFolder, zipFile);

                logArea.appendText("ZIP created:\n");
                logArea.appendText(zipFile.toString() + "\n");

            } else {

                logArea.appendText("Backup folder created:\n");
                logArea.appendText(backupFolder.toString() + "\n");

            }

            long fileCount = Files.walk(backupFolder)
                    .filter(Files::isRegularFile)
                    .count();

            long duration = System.currentTimeMillis() - startTime;

            LogService logger = new LogService();

            logger.writeLog(
                    "SUCCESS",
                    source,
                    backupFolder,
                    fileCount,
                    duration,
                    zipCheck.isSelected()
            );

            progressBar.setProgress(1);

            statusLabel.setText("Backup completed successfully.");

            logArea.appendText("\n");
            logArea.appendText("Files copied : " + fileCount + "\n");
            logArea.appendText("Duration     : " + duration + " ms\n");
            logArea.appendText("Backup finished.\n\n");

        } catch (Exception e) {

            progressBar.setProgress(0);

            statusLabel.setText("Backup failed.");

            logArea.appendText("ERROR:\n");
            logArea.appendText(e.getMessage() + "\n\n");

            try {

                LogService logger = new LogService();

                logger.writeLog(
                        "FAILED",
                        Paths.get(sourceField.getText()),
                        Paths.get(destinationField.getText()),
                        0,
                        0,
                        zipCheck.isSelected()
                );

            } catch (Exception ignored) {
            }

            e.printStackTrace();

        }

    }

}