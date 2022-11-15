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
public class CoronaDto {
	private String name;
	private String address;
	private String weekday;
	private String saterday;
	private String sunday;
	private String phonenumber;
	private String conv;
}
