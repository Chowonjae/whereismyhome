package com.ssafy.map.model.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ssafy.map.model.CoronaDto;
import com.ssafy.map.model.DealDto;
import com.ssafy.map.model.HospitalDto;
import com.ssafy.map.model.InterDto;
import com.ssafy.map.model.AptDto;
import com.ssafy.map.model.MetroDto;
import com.ssafy.map.model.SidoGugunCodeDto;
import com.ssafy.map.model.StarBucksDto;

public interface MapService {

	ArrayList<AptDto> searchArea(String regCode) throws SQLException;
	ArrayList<DealDto> searchApt(String aptCode) throws SQLException;

	ArrayList<InterDto> getInterDto(String userId) throws SQLException;

	void addinter(InterDto interDto) throws SQLException;

	int interDupCheck(Map<String, String> map) throws SQLException;

	void delinter(Map<String, String> map) throws SQLException;

	ArrayList<CoronaDto> corona(Map<String, String> map) throws SQLException;

	ArrayList<HospitalDto> hospital(String gugun) throws SQLException;

	List<SidoGugunCodeDto> getSido() throws Exception;

	List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception;

	List<SidoGugunCodeDto> getDongInGugun(String gugun) throws Exception;

//	StarBucksDto getCoffeeDto(Map<String, String> map) throws SQLException;
//
//	MetroDto getMetroDto(Map<String, String> map) throws SQLException;
}
