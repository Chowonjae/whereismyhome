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
public class DealDto {
	private String no;
	private String aptCode;
	private String area;
	private String dealAmount;
	private int dealYear;
	private int dealMonth;
	private int dealDay;
}
