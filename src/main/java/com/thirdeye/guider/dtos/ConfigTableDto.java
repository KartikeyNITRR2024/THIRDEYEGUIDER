package com.thirdeye.guider.dtos;

import java.sql.Time;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ConfigTableDto {
	 private Long id;
	 private Integer noOfMachineForLiveMarket;
	 private Integer noOfMachineForHoldedStokes;
	 private Long timeGap;
	 private Time startTime;
	 private Time endTime;
	 private Time resettime;
	 private Integer noofuser;
	 private String telegramBotToken;
	 private String password;
	 private Integer noOfStockForMorningUpdate;
	 private Time morningPriceUpdateTime;
	 private Time eveningPriceUpdateTime;
}
