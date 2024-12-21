package com.thirdeye.guider.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thirdeye.guider.annotation.AdminRequired;
import com.thirdeye.guider.dtos.ConfigTableDto;
import com.thirdeye.guider.pojos.SetConfigResponse;
import com.thirdeye.guider.services.ConfigService;
import com.thirdeye.guider.utils.AllMicroservicesData;

@RestController
@RequestMapping("/api/config")
public class ConfigController {

	@Autowired
	AllMicroservicesData allMicroservicesData;
	
	@Autowired
	private ConfigService configService;
	
    private static final Logger logger = LoggerFactory.getLogger(ConfigController.class);

    @PostMapping("/{uniqueId}/getconfig")
    @AdminRequired
    public ResponseEntity<ConfigTableDto> getConfig(@PathVariable("uniqueId") Integer pathUniqueId, @RequestBody ConfigTableDto configTableDto) {
        if (pathUniqueId.equals(allMicroservicesData.current.getMicroserviceUniqueId())) {
            logger.info("Status check for uniqueId {}: Found", allMicroservicesData.current.getMicroserviceUniqueId());
        	ConfigTableDto configTableDto1;
			try {
				configTableDto1 = configService.getConfig(configTableDto);
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(401).build();
			}
            return ResponseEntity.ok(configTableDto1);
        } else {
            logger.warn("Status check for uniqueId {}: Not Found", allMicroservicesData.current.getMicroserviceUniqueId());
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/{uniqueId}/setconfig")
    @AdminRequired
    public ResponseEntity<SetConfigResponse> setConfig(@PathVariable("uniqueId") Integer pathUniqueId, @RequestBody ConfigTableDto configTableDto) {
        if (pathUniqueId.equals(allMicroservicesData.current.getMicroserviceUniqueId())) {
            logger.info("Status check for uniqueId {}: Found", allMicroservicesData.current.getMicroserviceUniqueId());
            SetConfigResponse setConfigResponse;
			try {
				setConfigResponse = configService.setConfig(configTableDto);
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(401).build();
			}
            return ResponseEntity.ok(setConfigResponse);
        } else {
            logger.warn("Status check for uniqueId {}: Not Found", allMicroservicesData.current.getMicroserviceUniqueId());
            return ResponseEntity.notFound().build();
        }
    }
    
    
}
