package com.thirdeye.guider.services;

import java.util.List;

import com.thirdeye.guider.dtos.ConfigTableDto;
import com.thirdeye.guider.pojos.SetConfigResponse;

public interface ConfigService {
	ConfigTableDto getConfig(ConfigTableDto configTableDto) throws Exception;
	SetConfigResponse setConfig(ConfigTableDto configTableDto) throws Exception;
}
