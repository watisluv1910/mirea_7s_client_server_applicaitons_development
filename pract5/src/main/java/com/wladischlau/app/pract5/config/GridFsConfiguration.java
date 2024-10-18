package com.wladischlau.app.pract5.config;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

public class GridFsConfiguration extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.gridfs.database}")
    private String databaseName;

    private final MappingMongoConverter mappingMongoConverter;

    public GridFsConfiguration(MappingMongoConverter mappingMongoConverter) {
        this.mappingMongoConverter = mappingMongoConverter;
    }

    @Bean
    public GridFsTemplate gridFsTemplate() {
        return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter);
    }

    @Override
    protected @NonNull String getDatabaseName() {
        return databaseName;
    }
}