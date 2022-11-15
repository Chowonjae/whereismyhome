package com.ssafy.map.model;

import org.springframework.stereotype.Repository;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "SidoGugunCodeDto : 시도, 구군, 동 정보", description = "시도, 구군, 동의 이름을 나타낸다.")
@Repository
@Getter
@Setter
@ToString
@NoArgsConstructor
public class SidoGugunCodeDto {

	@ApiModelProperty(value = "시도코드")
	private String sidoCode;
	@ApiModelProperty(value = "시도이름")
	private String sidoName;
	@ApiModelProperty(value = "구군코드")
	private String gugunCode;
	@ApiModelProperty(value = "구군이름")
	private String gugunName;
	@ApiModelProperty(value = "동코드")
	private String dongCode;
	@ApiModelProperty(value = "동이름")
	private String dongName;

}
