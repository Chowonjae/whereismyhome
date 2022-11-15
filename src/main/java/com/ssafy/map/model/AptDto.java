package com.ssafy.map.model;

import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Repository
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AptDto {
	private String aptCode;
	private String dongCode;
	private String apartmentName;
	private String lat; // 위도
	private String lng; // 경도
	private int buildYear; // 건축년도
	private StarBucksDto coffee;
	private MetroDto metro;
}
