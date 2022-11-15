package com.ssafy.map.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.map.model.AptDto;
import com.ssafy.map.model.CoronaDto;
import com.ssafy.map.model.DealDto;
import com.ssafy.map.model.HospitalDto;
import com.ssafy.map.model.InterDto;
import com.ssafy.map.model.SidoGugunCodeDto;
import com.ssafy.map.model.service.MapService;

import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/map")
@CrossOrigin("*")
public class MapRestController {
	
	@Autowired
	private MapService mapService;

	@GetMapping("/sido")
	public ResponseEntity<List<SidoGugunCodeDto>> sido() throws Exception {
		return new ResponseEntity<List<SidoGugunCodeDto>>(mapService.getSido(), HttpStatus.OK);
	}

	@GetMapping("/gugun")
	public ResponseEntity<List<SidoGugunCodeDto>> gugun(
			@RequestParam("sido") @ApiParam(value = "시도코드.", required = true) String sido) throws Exception {
		return new ResponseEntity<List<SidoGugunCodeDto>>(mapService.getGugunInSido(sido), HttpStatus.OK);
	}	
	@GetMapping("/dong")
	public ResponseEntity<List<SidoGugunCodeDto>> dong(
			@RequestParam("sido") @ApiParam(value = "구군코드.", required = true) String gugun) throws Exception {
		System.out.println("여기 왔음");
		return new ResponseEntity<List<SidoGugunCodeDto>>(mapService.getDongInGugun(gugun), HttpStatus.OK);
	}	
	// 지역 내 아파트 조회
	@GetMapping(value = "/search/{dong}")
	public ResponseEntity<?> searchArea(@PathVariable String dong){
		try {
			List<AptDto> list = mapService.searchArea(dong);
			return new ResponseEntity<List<AptDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	// 해당 아파트 거래내역 조회
	@GetMapping(value = "/search/apt/{aptCode}")
	public ResponseEntity<?> searchApt(@PathVariable String aptCode){
		try {
			List<DealDto> list = mapService.searchApt(aptCode);
			return new ResponseEntity<List<DealDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}	
	
	// 관심지역 조회
	@GetMapping(value = "/inter/{userId}")
	public ResponseEntity<?> getInterDto(@PathVariable String userId){
		try {
			List<InterDto> list = mapService.getInterDto(userId);
			return new ResponseEntity<List<InterDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}	
	@PostMapping(value = "/inter")
	public ResponseEntity<?> addinter(@RequestBody InterDto interDto){
		try {
			System.out.println(interDto);
			mapService.addinter(interDto);
			List<InterDto> list = mapService.getInterDto(interDto.getUserId());
			return new ResponseEntity<List<InterDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@GetMapping(value = "/inter/{userId}/{dongCode}")
	public ResponseEntity<?> interDupCheck(@PathVariable String userId, @PathVariable String dongCode){
		try {
			Map<String,String> map = new HashMap<String,String>();
			map.put("userId", userId);
			map.put("dongCode", dongCode);
			int cnt = mapService.interDupCheck(map);
			if(cnt!=0)
				return new ResponseEntity<Integer>(cnt, HttpStatus.FOUND);
			else
				return new ResponseEntity<Integer>(cnt, HttpStatus.OK);
				
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@DeleteMapping(value = "/inter/{userId}/{dongCode}")
	public ResponseEntity<?> delinter(@PathVariable String userId, @PathVariable String dongCode){
		try {
			Map<String,String> map = new HashMap<String,String>();
			map.put("userId", userId);
			map.put("dongCode", dongCode);
			mapService.delinter(map);
			List<InterDto> list = mapService.getInterDto(map.get("userId"));
			return new ResponseEntity<List<InterDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	//병원
	@GetMapping(value = "/hospital/{gugun}")
	public ResponseEntity<?> hospital(@PathVariable String gugun){
		try {
			List<HospitalDto> list = mapService.hospital(gugun);
			return new ResponseEntity<List<HospitalDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}	
	
	//코로나
	@GetMapping(value = "/corona/{sido}/{gugun}/{day}/{night}/{hour}/{min}")
	public ResponseEntity<?> corona(@PathVariable String sido,@PathVariable String gugun,@PathVariable String day,@PathVariable String night,@PathVariable String hour,@PathVariable String min){
		try {
			Map<String,String> map = new HashMap<String,String>();
			System.out.println(sido+" "+gugun);
			map.put("sido", sido);
			map.put("gugun", gugun);
			map.put("day", day);
			map.put("night", night);
			map.put("hour", hour);
			map.put("min", min);
			List<CoronaDto> list = mapService.corona(map);
			return new ResponseEntity<List<CoronaDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}		
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
