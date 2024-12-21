package com.thirdeye.guider.pojos;

import java.util.Map;

import com.thirdeye.guider.dtos.ConfigTableDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SetConfigResponse {
   private ConfigTableDto configTableDto;
   private Map<String, Boolean> status;
}
