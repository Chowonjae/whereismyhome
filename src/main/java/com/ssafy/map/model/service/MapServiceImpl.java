package com.ssafy.map.model.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.map.model.CoronaDto;
import com.ssafy.map.model.HospitalDto;
import com.ssafy.map.model.InterDto;
import com.ssafy.map.model.MapDto;
import com.ssafy.map.model.MetroDto;
import com.ssafy.map.model.StarBucksDto;
import com.ssafy.map.model.dao.MapDao;

@Service
public class MapServiceImpl implements MapService {//여기서 무엇을 하느냐?
	
	@Autowired
	private MapDao mapDao;

	@Override
	public ArrayList<MapDto> search(Map<String,String> map) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<MapDto> list  =  mapDao.search(map);
		
		for(MapDto mapDto:list) {
			map.put("lat", mapDto.getLat());
			map.put("lng", mapDto.getLng());
			mapDto.setCoffee(mapDao.getCoffee(map));
			mapDto.setMetro(mapDao.getMetro(map));
			System.out.println(mapDto);
		}
		return list;
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
	public int interDupCheck(Map<String,String> map) throws SQLException {
		// TODO Auto-generated method stub
		return mapDao.interDupCheck(map);
	}

	@Override
	public void delinter(Map<String,String> map) throws SQLException {
		mapDao.delinter(map);
		
	}
	@Override
	public ArrayList<CoronaDto> corona(Map<String,String> map) throws SQLException {
		// TODO Auto-generated method stub
		map.put("sido", mapDao.getSido(map.get("sido")));
		map.put("gugun", mapDao.getGugun(map.get("gugun")));
		
		List<CoronaDto> list = mapDao.corona(map);
		ArrayList<CoronaDto> list1 = new ArrayList<CoronaDto>();
		String day = map.get("day");
		StringTokenizer stz;
		int checkHour = Integer.parseInt(map.get("hour"))+("PM".equals(map.get("night"))?12:0);
		int checkMin = Integer.parseInt(map.get("min"));
		switch (day) {
		case "WKD":
			for (CoronaDto coronaDto : list) {
				String s = coronaDto.getWeekday();
				String[] temp = s.split(",| ", -1);
				loop1 : for (int i = 0; i < temp.length ; i++) {
					if(temp[i].matches("^[0-9]*:[0-9]*~[0-9]*:[0-9]*")) {
						String[] check = temp[i].split("~");
						stz = new StringTokenizer(check[0], ":");
						int startHour = Integer.parseInt(stz.nextToken());
						int startMin = Integer.parseInt(stz.nextToken());
						stz = new StringTokenizer(check[1], ":");
						int endHour = Integer.parseInt(stz.nextToken());
						int endMin = Integer.parseInt(stz.nextToken());
						if(checkHour < startHour) continue loop1;
						else if(checkHour == startHour) {
							if(checkMin < startMin) continue loop1;
						}
						else if(checkHour == endHour) {
							if(checkMin > endMin) continue loop1;
						}
						else if(checkHour > endHour) continue loop1;
						list1.add(coronaDto);
					}
				}
			}
			
			break;
		case "SAT":
			for (CoronaDto coronaDto : list) {
				String s = coronaDto.getSaterday();
				String[] temp = s.split(",| ", -1);
				loop1 : for (int i = 0; i < temp.length ; i++) {
					if(temp[i].matches("^[0-9]*:[0-9]*~[0-9]*:[0-9]*")) {
						String[] check = temp[i].split("~");
						stz = new StringTokenizer(check[0], ":");
						int startHour = Integer.parseInt(stz.nextToken());
						int startMin = Integer.parseInt(stz.nextToken());
						stz = new StringTokenizer(check[1], ":");
						int endHour = Integer.parseInt(stz.nextToken());
						int endMin = Integer.parseInt(stz.nextToken());
						if(checkHour < startHour) continue loop1;
						else if(checkHour == startHour) {
							if(checkMin < startMin) continue loop1;
						}
						else if(checkHour == endHour) {
							if(checkMin > endMin) continue loop1;
						}
						else if(checkHour > endHour) continue loop1;
						list1.add(coronaDto);
					}
				}
			}
			
			break;
		case "SUN":
			for (CoronaDto coronaDto : list) {
				String s = coronaDto.getSunday();
				String[] temp = s.split(",| ", -1);
				loop1 : for (int i = 0; i < temp.length ; i++) {
					if(temp[i].matches("^[0-9]*:[0-9]*~[0-9]*:[0-9]*")) {
						String[] check = temp[i].split("~");
						stz = new StringTokenizer(check[0], ":");
						int startHour = Integer.parseInt(stz.nextToken());
						int startMin = Integer.parseInt(stz.nextToken());
						stz = new StringTokenizer(check[1], ":");
						int endHour = Integer.parseInt(stz.nextToken());
						int endMin = Integer.parseInt(stz.nextToken());
						if(checkHour < startHour) continue loop1;
						else if(checkHour == startHour) {
							if(checkMin < startMin) continue loop1;
						}
						else if(checkHour == endHour) {
							if(checkMin > endMin) continue loop1;
						}
						else if(checkHour > endHour) continue loop1;
						list1.add(coronaDto);
					}
				}
			}
			
			break;
		}
		return list1;
	}
	@Override
	public ArrayList<HospitalDto> hospital(Map<String,String> map) throws SQLException {
		map.put("sido", mapDao.getSido(map.get("sido")));
		map.put("gugun", mapDao.getGugun(map.get("gugun")));
		return mapDao.hospital(map);
	}
//	@Override
//	public StarBucksDto getCoffeeDto(Map<String,String> map) throws SQLException {
//		StarBucksDto coffee = mapDao.getCoffee(map);
//		if(coffee!=null)
//			coffee.setDist(getDist(map.get("lat"),map.get("lng"),coffee.getLat(),coffee.getLng()));
//		return coffee;
//	}
//	@Override
//	public MetroDto getMetroDto(Map<String,String> map) throws SQLException {
//		MetroDto metro = mapDao.getMetro(map);
//		if(metro != null)
//			metro.setDist(getDist(map.get("lat"),map.get("lng"),metro.getLat(),metro.getLng()));
//		return metro;
//	}
//	
//	private int getDist(String lat1, String lng1, String lat2, String lng2) {
//		double lt1 = Double.parseDouble(lat1);
//		double ln1 = Double.parseDouble(lng1);
//		double lt2 = Double.parseDouble(lat2);
//		double ln2 = Double.parseDouble(lng2);
//		
//		double X = ( Math.cos(lt1) * 6400 * 2 * 3.14 / 360 ) * Math.abs(ln1-ln2);
//
//		double Y = 111 * Math.abs(lt1-lt2);
//
//		int D = (int)(Math.sqrt(X*X+Y*Y)*1000);
//		return D;
//	}
	
	
}

