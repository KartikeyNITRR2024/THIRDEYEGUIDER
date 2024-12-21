package com.thirdeye.guider.controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thirdeye.guider.annotation.AdminRequired;
import com.thirdeye.guider.services.UpdateInitiaterService;
import com.thirdeye.guider.utils.AllMicroservicesData;


@RestController
@RequestMapping("/api/update")
public class UpdateInitiaterController {

	@Autowired
	AllMicroservicesData allMicroservicesData;
	
	@Autowired
	private UpdateInitiaterService updateInitiaterService;
	
	@Value("${updaterMicroserviceName}")
    private String updaterMicroserviceName;
	
	private List<String> updaterMicroservicesName;

    @Value("${updaterMicroservicesName}")
    public void setMicroservicesNames(String microservicesNames) {
        this.updaterMicroservicesName = Arrays.asList(microservicesNames.split(","));
    }
	
    private static final Logger logger = LoggerFactory.getLogger(UpdateInitiaterController.class);

    @PostMapping("/{uniqueId}/{uniqueIdOfSender}")
    public ResponseEntity<Boolean> updateAll(@PathVariable("uniqueId") Integer pathUniqueId, @PathVariable("uniqueIdOfSender") Integer pathUniqueIdOfSender) {
        if (pathUniqueId.equals(allMicroservicesData.current.getMicroserviceUniqueId()) && pathUniqueIdOfSender.equals(allMicroservicesData.allMicroservices.get(updaterMicroserviceName).getMicroserviceUniqueId())) {
            logger.info("Status check for uniqueId {} and uniqueIdOfSender {}: Found", allMicroservicesData.current.getMicroserviceUniqueId(), allMicroservicesData.allMicroservices.get(updaterMicroserviceName).getMicroserviceUniqueId());
			try {
				updateInitiaterService.initiateUpdate();
			} catch (Exception e) {
				return ResponseEntity.ok(Boolean.FALSE);
			}
            return ResponseEntity.ok(Boolean.TRUE);
        } else {
            logger.warn("Status check for uniqueId {} or uniqueIdOfSender {}: Not Found", allMicroservicesData.current.getMicroserviceUniqueId(), allMicroservicesData.allMicroservices.get(updaterMicroserviceName).getMicroserviceUniqueId());
            return ResponseEntity.notFound().build();
        }
    }   
    
    @PostMapping("updateallmicroservicesfromfrontend/{uniqueId}/{updateSteps}")
    @AdminRequired
    public ResponseEntity<Map<String,Boolean>> updateAllMicroservicesFromFrontEnd(@PathVariable("uniqueId") Integer pathUniqueId, @PathVariable("updateSteps") Integer updateSteps) {
        if (pathUniqueId.equals(allMicroservicesData.current.getMicroserviceUniqueId()) && (updateSteps == 0 || updateSteps == 1)) {
            logger.info("Status check for uniqueId {} and uniqueIdOfSender {}: Found", allMicroservicesData.current.getMicroserviceUniqueId());
            Map<String,Boolean> updateStatus = new HashMap<>();
			updateStatus = updateInitiaterService.updataAllMicroservices(updateSteps);
            return ResponseEntity.ok(updateStatus);
        } else {
            logger.warn("Status check for uniqueId {} or uniqueIdOfSender {}: Not Found", allMicroservicesData.current.getMicroserviceUniqueId());
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("updateallmicroservices/{uniqueId}/{uniqueIdOfSender}/{updateSteps}")
    public ResponseEntity<Map<String,Boolean>> updateAllMicroservices(@PathVariable("uniqueId") Integer pathUniqueId, @PathVariable("uniqueIdOfSender") Integer pathUniqueIdOfSender, @PathVariable("updateSteps") Integer updateSteps) {
    	boolean isAllowedMicroservices = updaterMicroservicesName.stream()
    	        .anyMatch(updaterMicroserviceName -> 
    	            pathUniqueIdOfSender.equals(allMicroservicesData.allMicroservices
    	                    .get(updaterMicroserviceName)
    	                    .getMicroserviceUniqueId())
    	        );
        if (pathUniqueId.equals(allMicroservicesData.current.getMicroserviceUniqueId()) && isAllowedMicroservices && (updateSteps == 0 || updateSteps == 1)) {
            logger.info("Status check for uniqueId {} and uniqueIdOfSender {}: Found", allMicroservicesData.current.getMicroserviceUniqueId(), pathUniqueIdOfSender);
            Map<String,Boolean> updateStatus = new HashMap<>();
			updateStatus = updateInitiaterService.updataAllMicroservices(updateSteps);
            return ResponseEntity.ok(updateStatus);
        } else {
            logger.warn("Status check for uniqueId {} or uniqueIdOfSender {}: Not Found", allMicroservicesData.current.getMicroserviceUniqueId(), pathUniqueIdOfSender);
            return ResponseEntity.notFound().build();
        }
    }   
}

