package com.ssafy.news.controller;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.news.model.NewsDto;

@RestController
@RequestMapping("/news")
@CrossOrigin("*")
public class NewsRestController {


	@GetMapping(value = "/{gugun}")
	public ResponseEntity<?> searchArea(@PathVariable String gugun){
		try {
			List<NewsDto> list = new ArrayList<NewsDto>();
			
			String sido = gugun.substring(0, 2).concat("00000000");
			gugun = gugun.concat("00000");
			String address = "https://land.naver.com/news/region.naver?city_no="+sido+"&dvsn_no="+gugun;
			//System.out.println(address);
			Document rawData = Jsoup.connect(address).timeout(5000).get();
			
			//System.out.println(rawData);
			
			Elements blogOption = rawData.select(".headline_list li");
			
			for(Element option : blogOption) {
				NewsDto news = new NewsDto();
				//news.setDate(option.select(".date").text());
				news.setDate(option.select(".date").text());
				news.setUrl("https://land.naver.com"+option.select(".photo a").attr("href"));
				news.setImgUrl(option.select(".photo a img").attr("src"));
				news.setTitle(option.select(".photo a img").attr("alt"));
				news.setContent(option.select("dd").text());
				
				list.add(news);
			}
			
			return new ResponseEntity<List<NewsDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
