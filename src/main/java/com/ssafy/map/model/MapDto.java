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
public class MapDto {
	private String no;
	private String aptCode;
	private String dong;
	private String dongCode;
	private String apartmentName;
	private String area;
	private String dealAmount;
	private int dealYear;
	private int dealMonth;
	private int dealDay;
	private String lat; // 위도
	private String lng; // 경도
	private int buildYear; // 건축년도
	private String jibun; // 지번주소
	private StarBucksDto coffee;
	private MetroDto metro;
}
