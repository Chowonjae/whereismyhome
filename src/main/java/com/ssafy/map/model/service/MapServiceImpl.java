package com.ssafy.map.model.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.map.model.AptDto;
import com.ssafy.map.model.CoronaDto;
import com.ssafy.map.model.DealDto;
import com.ssafy.map.model.HospitalDto;
import com.ssafy.map.model.InterDto;
import com.ssafy.map.model.MetroDto;
import com.ssafy.map.model.SchoolDto;
import com.ssafy.map.model.SidoGugunCodeDto;
import com.ssafy.map.model.StarBucksDto;
import com.ssafy.map.model.dao.MapDao;

@Service
public class MapServiceImpl implements MapService {// 여기서 무엇을 하느냐?

	@Autowired
	private MapDao mapDao;

	@Override
	public List<SidoGugunCodeDto> getSido() throws Exception {
		return mapDao.getSidos();
	}

	@Override
	public List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception {
		return mapDao.getGugunInSido(sido);
	}
	@Override
	public List<SidoGugunCodeDto> getDongInGugun(String gugun) throws Exception {
		return mapDao.getDongInGugun(gugun);
	}

	@Override
	public ArrayList<AptDto> searchArea(String regCode) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<AptDto> list = mapDao.searchArea(regCode);
//		Map<String, String> map = new HashMap<String, String>();
//		for (AptDto mapDto : list) {
//			map.put("lat", mapDto.getLat());
//			map.put("lng", mapDto.getLng());
//			mapDto.setCoffee(mapDao.getCoffee(map));
//			mapDto.setMetro(mapDao.getMetro(map));
//		}
		return list;
	}

	@Override
	public ArrayList<DealDto> searchApt(String aptCode) throws SQLException {

		return mapDao.searchApt(aptCode);
	}

	@Override
	public ArrayList<InterDto> getInterDto(String userId) throws SQLException {
		// TODO Auto-generated method stub
		return mapDao.getInterDto(userId);
	}

	@Override
	public void addinter(InterDto interDto) throws SQLException {
		mapDao.addinter(interDto);
	}

	@Override
	public int interDupCheck(Map<String, String> map) throws SQLException {
		// TODO Auto-generated method stub
		return mapDao.interDupCheck(map);
	}

	@Override
	public void delinter(Map<String, String> map) throws SQLException {
		mapDao.delinter(map);

	}

	@Override
	public ArrayList<CoronaDto> corona(Map<String, String> map) throws SQLException {
		// TODO Auto-generated method stub

		List<CoronaDto> list = mapDao.corona(map);
		ArrayList<CoronaDto> list1 = new ArrayList<CoronaDto>();
		String day = map.get("day");
		StringTokenizer stz;
		int checkHour = Integer.parseInt(map.get("hour")) + ("PM".equals(map.get("night")) ? 12 : 0);
		int checkMin = Integer.parseInt(map.get("min"));
		switch (day) {
		case "WKD":
			for (CoronaDto coronaDto : list) {
				String s = coronaDto.getWeekday();
				String[] temp = s.split(",| ", -1);
				loop1: for (int i = 0; i < temp.length; i++) {
					if (temp[i].matches("^[0-9]*:[0-9]*~[0-9]*:[0-9]*")) {
						String[] check = temp[i].split("~");
						stz = new StringTokenizer(check[0], ":");
						int startHour = Integer.parseInt(stz.nextToken());
						int startMin = Integer.parseInt(stz.nextToken());
						stz = new StringTokenizer(check[1], ":");
						int endHour = Integer.parseInt(stz.nextToken());
						int endMin = Integer.parseInt(stz.nextToken());
						if (checkHour < startHour)
							continue loop1;
						else if (checkHour == startHour) {
							if (checkMin < startMin)
								continue loop1;
						} else if (checkHour == endHour) {
							if (checkMin > endMin)
								continue loop1;
						} else if (checkHour > endHour)
							continue loop1;
						list1.add(coronaDto);
					}
				}
			}

			break;
		case "SAT":
			for (CoronaDto coronaDto : list) {
				String s = coronaDto.getSaterday();
				String[] temp = s.split(",| ", -1);
				loop1: for (int i = 0; i < temp.length; i++) {
					if (temp[i].matches("^[0-9]*:[0-9]*~[0-9]*:[0-9]*")) {
						String[] check = temp[i].split("~");
						stz = new StringTokenizer(check[0], ":");
						int startHour = Integer.parseInt(stz.nextToken());
						int startMin = Integer.parseInt(stz.nextToken());
						stz = new StringTokenizer(check[1], ":");
						int endHour = Integer.parseInt(stz.nextToken());
						int endMin = Integer.parseInt(stz.nextToken());
						if (checkHour < startHour)
							continue loop1;
						else if (checkHour == startHour) {
							if (checkMin < startMin)
								continue loop1;
						} else if (checkHour == endHour) {
							if (checkMin > endMin)
								continue loop1;
						} else if (checkHour > endHour)
							continue loop1;
						list1.add(coronaDto);
					}
				}
			}

			break;
		case "SUN":
			for (CoronaDto coronaDto : list) {
				String s = coronaDto.getSunday();
				String[] temp = s.split(",| ", -1);
				loop1: for (int i = 0; i < temp.length; i++) {
					if (temp[i].matches("^[0-9]*:[0-9]*~[0-9]*:[0-9]*")) {
						String[] check = temp[i].split("~");
						stz = new StringTokenizer(check[0], ":");
						int startHour = Integer.parseInt(stz.nextToken());
						int startMin = Integer.parseInt(stz.nextToken());
						stz = new StringTokenizer(check[1], ":");
						int endHour = Integer.parseInt(stz.nextToken());
						int endMin = Integer.parseInt(stz.nextToken());
						if (checkHour < startHour)
							continue loop1;
						else if (checkHour == startHour) {
							if (checkMin < startMin)
								continue loop1;
						} else if (checkHour == endHour) {
							if (checkMin > endMin)
								continue loop1;
						} else if (checkHour > endHour)
							continue loop1;
						list1.add(coronaDto);
					}
				}
			}

			break;
		}
		return list1;
	}

	@Override
	public ArrayList<HospitalDto> hospital(String gugun) throws SQLException {
		return mapDao.hospital(gugun);
	}
	@Override
	public StarBucksDto getCoffee(Map<String, String> map) throws SQLException {
		return mapDao.getCoffee(map);
	}
	@Override
	public MetroDto getMetro(Map<String, String> map) throws SQLException {
		return mapDao.getMetro(map);
	}

	@Override
	public SchoolDto getSchool(Map<String, String> map) throws SQLException {
		return mapDao.getSchool(map);
	}
	@Override
	public List<SchoolDto> getSchools(String dongCode) throws SQLException {
		return mapDao.getSchools(dongCode);
	}
	
	@Override
	public List<StarBucksDto> getCoffees() throws SQLException {
		return mapDao.getCoffees();
	}
	@Override
	public List<MetroDto> getMetros() throws SQLException {
		return mapDao.getMetros();
	}

}
