package com.thirdeye.guider.services.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.thirdeye.guider.services.UpdateInitiaterService;
import com.thirdeye.guider.utils.AllMicroservicesData;
import com.thirdeye.guider.utils.Initiatier;
import com.thirdeye.guider.entity.MicroservicesInfo;

@Service
public class UpdateInitiaterServiceImpl implements UpdateInitiaterService {
	
	@Autowired
	private Initiatier initiatier;
	
	@Autowired
	AllMicroservicesData allMicroservicesData;
	
	@Autowired
    private RestTemplate restTemplate;
	
	private static final Logger logger = LoggerFactory.getLogger(UpdateInitiaterServiceImpl.class);
	
	@Override
	public void initiateUpdate() throws Exception {
        try {
        	logger.info("Going to update initiate");
            initiatier.init();
            logger.info("Successfully updated initiate");
        } catch (Exception e) {
            e.printStackTrace(); 
            logger.info("Failed to updated initiate");
            throw new Exception("Failed to updated initiate");
        }
    }
	
	@Override
	public Map<String, Boolean> updataAllMicroservices(Integer updateSteps)
	{
		Map<String, Boolean> status = new HashMap<>();
		if(updateSteps.equals(1))
		{
			Boolean status1 = checkStatus(allMicroservicesData.current);
			status.put(allMicroservicesData.current.getMicroserviceName(), status1);
		}
		allMicroservicesData.allMicroservices.forEach((key, value) -> {
		    if(key.equalsIgnoreCase(allMicroservicesData.current.getMicroserviceName()))
		    {
		    	return;
		    }
		    Boolean status1 = checkStatus(value);
		    status.put(key, status1);
		});
		if(updateSteps.equals(0))
		{
			Boolean status1 = checkStatus(allMicroservicesData.current);
			status.put(allMicroservicesData.current.getMicroserviceName(), status1);
		}
		return status;
	}
	
	@Override
	public Boolean checkStatus(MicroservicesInfo microserviceData) {
      try { 
    	  logger.info("Updating microservice : {} ", microserviceData.getMicroserviceName());
          String url = microserviceData.getMicroserviceUrl() + "api/update/" + microserviceData.getMicroserviceUniqueId() + "/" + allMicroservicesData.current.getMicroserviceUniqueId();
          logger.info("Url is : {} ", url);
          ResponseEntity<Boolean> response = restTemplate.postForEntity(url, null, Boolean.class);
          if(response.getBody() != null && response.getBody())
          {
        	  return response.getBody(); 
          }
          return Boolean.FALSE;
      } catch (Exception e) {
          logger.warn("Error while updateing initiater: ", e);
          return Boolean.FALSE; 
      }
   }
}
