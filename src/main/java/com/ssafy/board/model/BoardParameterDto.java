package com.ssafy.board.model;

import org.springframework.stereotype.Repository;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Repository
@Getter
@Setter
@ToString
public class BoardParameterDto {
	private int pg;
	private int spp;
	private int start;
	private String key;
	private String word;
	
	public BoardParameterDto() {
		pg = 1;
		spp = 20;
	}
	
	public void setPg(int pg) {
		pg = pg == 0 ? 1 : pg;
		this.pg = pg;
	}
}
