package com.ssafy.news.model;

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
public class NewsDto {
	private String title;
	private String imgUrl;
	private String date;
	private String url;
	private String content;
	private String brand;
}
