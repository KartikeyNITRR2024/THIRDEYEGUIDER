package com.thirdeye.guider.utils;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.thirdeye.guider.entity.ConfigTable;
import com.thirdeye.guider.entity.ConfigUsed;
import com.thirdeye.guider.repositories.ConfigTableRepo;
import com.thirdeye.guider.repositories.ConfigUsedRepo;

import lombok.Getter;
import lombok.Setter;


@Component 
@Getter
@Setter
public class PropertyLoader {

    private Long configId;
    private String password;
    

    private static final Logger logger = LoggerFactory.getLogger(PropertyLoader.class);

    @Autowired
    private ConfigTableRepo configTableRepo;
    
    @Autowired
    private ConfigUsedRepo configUsedRepo;
    

    public void updatePropertyLoader() {
        try {
            logger.info("Fetching currently config used.");
            ConfigUsed configUsed = configUsedRepo.findById(1L).get();
            configId = configUsed.getId();
            password = configUsed.getConfigPassword();
            logger.info("Fetching configuration for configId: {}", configId);
            Optional<ConfigTable> configTable = configTableRepo.findById(configId);
            if (configTable.isPresent()) {
            } else {
                logger.warn("No configuration found for configId: {}", configId);
            }
        } catch (Exception e) {
            logger.error("An error occurred while fetching configuration: {}", e.getMessage(), e);
        }
    }
}
