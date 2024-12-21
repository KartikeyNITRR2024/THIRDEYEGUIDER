package com.thirdeye.guider.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.thirdeye.guider.controllers.StatusCheckerController;
import com.thirdeye.guider.dtos.ConfigTableDto;
import com.thirdeye.guider.entity.ConfigTable;
import com.thirdeye.guider.pojos.SetConfigResponse;
import com.thirdeye.guider.repositories.ConfigTableRepo;
import com.thirdeye.guider.services.ConfigService;
import com.thirdeye.guider.services.UpdateInitiaterService;
import com.thirdeye.guider.utils.AllMicroservicesData;
import com.thirdeye.guider.utils.PropertyLoader;

@Service
public class ConfigServiceImpl implements ConfigService  {

	@Autowired
	ConfigTableRepo configTableRepo;
	
	@Autowired
	AllMicroservicesData allMicroservicesData;
	
	@Autowired
	UpdateInitiaterService updateInitiaterService;
	
	@Autowired
	PropertyLoader propertyLoader;
	
	private static final Logger logger = LoggerFactory.getLogger(ConfigServiceImpl.class);
	
	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	
	@Override
	public ConfigTableDto getConfig(ConfigTableDto configTableDto) throws Exception
	{
		if(!configTableDto.getPassword().equalsIgnoreCase(propertyLoader.getPassword()))
		{
			throw new Exception("Invalid Password");
		}
		ConfigTable configTable = configTableRepo.findById(propertyLoader.getConfigId()).get();
		return toDto(configTable);
	}
	
	@Override
	public SetConfigResponse setConfig(ConfigTableDto configTableDto) throws Exception {
		if(!configTableDto.getPassword().equalsIgnoreCase(propertyLoader.getPassword()))
		{
			throw new Exception("Invalid Password");
		}
		SetConfigResponse setConfigResponse = null;
		lock.writeLock().lock();
		try {
			List<Integer> configChanges = new ArrayList<>();
			ConfigTable configTable = configTableRepo.findById(propertyLoader.getConfigId()).get();
			if(configTableDto.getNoOfMachineForLiveMarket() != null && configTableDto.getNoOfMachineForLiveMarket() > 0)
			{
				configChanges.add(1);
				configTable.setNoOfMachineForLiveMarket(configTableDto.getNoOfMachineForLiveMarket());
			}
			if(configTableDto.getNoOfMachineForHoldedStokes() != null && configTableDto.getNoOfMachineForHoldedStokes() > 0)
			{
				configChanges.add(2);
				configTable.setNoOfMachineForHoldedStokes(configTableDto.getNoOfMachineForHoldedStokes());
			}
			if(configTableDto.getTimeGap() != null && configTableDto.getTimeGap() > 0)
			{
				configChanges.add(3);
				configTable.setTimeGap(configTableDto.getTimeGap());
			}
			if(configTableDto.getStartTime() != null)
			{
				configChanges.add(4);
				configTable.setStartTime(configTableDto.getStartTime());
			}
			if(configTableDto.getEndTime() != null)
			{
				configChanges.add(5);
				configTable.setEndTime(configTableDto.getEndTime());
			}
			if(configTableDto.getResettime() != null)
			{
				configChanges.add(6);
				configTable.setResettime(configTableDto.getResettime());
			}
			if(configTableDto.getNoofuser() != null && configTableDto.getNoofuser() > 0)
			{
				configChanges.add(7);
				configTable.setNoofuser(configTableDto.getNoofuser());
			}
			if(configTableDto.getTelegramBotToken() != null && configTableDto.getTelegramBotToken().length() > 0)
			{
				configChanges.add(8);
				configTable.setTelegramBotToken(configTableDto.getTelegramBotToken());
			}
			if(configTableDto.getNoOfStockForMorningUpdate() != null && configTableDto.getNoOfStockForMorningUpdate() > 0)
			{
				configChanges.add(9);
				configTable.setNoOfStockForMorningUpdate(configTableDto.getNoOfStockForMorningUpdate());
			}
			if(configTableDto.getMorningPriceUpdateTime() != null)
			{
				configChanges.add(9);
				configTable.setMorningPriceUpdateTime(configTableDto.getMorningPriceUpdateTime());
			}
			if(configTableDto.getEveningPriceUpdateTime() != null)
			{
				configChanges.add(9);
				configTable.setEveningPriceUpdateTime(configTableDto.getEveningPriceUpdateTime());
			}
			ConfigTable updatedConfigTable = configTableRepo.save(configTable);
			Map<String, Boolean> status = updateInitiaterService.updataAllMicroservices(0);
			setConfigResponse = new SetConfigResponse(toDto(updatedConfigTable),status);
		} finally {
	        lock.writeLock().unlock();
	    }
		return setConfigResponse;
	}
	
	
	
	private ConfigTable toEntity(ConfigTableDto configTableDto)
	{
		ConfigTable configTable = new ConfigTable();
		configTable.setId(configTableDto.getId());
		configTable.setNoOfMachineForLiveMarket(configTableDto.getNoOfMachineForLiveMarket());
		configTable.setNoOfMachineForHoldedStokes(configTableDto.getNoOfMachineForHoldedStokes());
		configTable.setTimeGap(configTableDto.getTimeGap());
		configTable.setStartTime(configTableDto.getStartTime());
		configTable.setEndTime(configTableDto.getEndTime());
		configTable.setResettime(configTableDto.getResettime());
		configTable.setNoofuser(configTableDto.getNoofuser());
		configTable.setTelegramBotToken(configTableDto.getTelegramBotToken());
		configTable.setNoOfStockForMorningUpdate(configTableDto.getNoOfStockForMorningUpdate());
		configTable.setMorningPriceUpdateTime(configTableDto.getMorningPriceUpdateTime());
		configTable.setEveningPriceUpdateTime(configTableDto.getEveningPriceUpdateTime());
		return configTable;
	}
	
	private ConfigTableDto toDto(ConfigTable configTable)
	{
		ConfigTableDto configTableDto = new ConfigTableDto();
		configTableDto.setNoOfMachineForLiveMarket(configTable.getNoOfMachineForLiveMarket());
		configTableDto.setNoOfMachineForHoldedStokes(configTable.getNoOfMachineForHoldedStokes());
		configTableDto.setTimeGap(configTable.getTimeGap());
		configTableDto.setStartTime(configTable.getStartTime());
		configTableDto.setEndTime(configTable.getEndTime());
		configTableDto.setResettime(configTable.getResettime());
		configTableDto.setNoofuser(configTable.getNoofuser());
		configTableDto.setTelegramBotToken(configTable.getTelegramBotToken());
		configTableDto.setNoOfStockForMorningUpdate(configTable.getNoOfStockForMorningUpdate());
		configTableDto.setMorningPriceUpdateTime(configTable.getMorningPriceUpdateTime());
		configTableDto.setEveningPriceUpdateTime(configTable.getEveningPriceUpdateTime());
		return configTableDto;
	}

	
}
