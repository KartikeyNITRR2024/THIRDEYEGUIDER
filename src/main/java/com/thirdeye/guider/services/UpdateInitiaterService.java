package com.thirdeye.guider.services;

import java.util.Map;

import com.thirdeye.guider.entity.MicroservicesInfo;

public interface UpdateInitiaterService {

	void initiateUpdate() throws Exception;
	Map<String, Boolean> updataAllMicroservices(Integer updateSteps);
	Boolean checkStatus(MicroservicesInfo microserviceData);

}
