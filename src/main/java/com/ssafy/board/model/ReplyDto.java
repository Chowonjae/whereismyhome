package com.ssafy.board.model;

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
public class ReplyDto {
	private int memo_no;
	private String user_id;
	private String comment;
	private String memo_time;
	private int article_no;
}
