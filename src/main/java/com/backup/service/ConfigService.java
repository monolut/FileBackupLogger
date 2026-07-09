package com.backup.service;

import com.backup.model.Config;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ConfigService {

    private final ObjectMapper mapper = new ObjectMapper();

    private final File file = new File("config.json");

    public Config load() throws IOException {

        if (!file.exists()) {

            Config config = new Config();

            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(file, config);

            return config;
        }

        return mapper.readValue(file, Config.class);
    }

    public void save(Config config) throws IOException {

        mapper.writerWithDefaultPrettyPrinter()
                .writeValue(file, config);

    }

}