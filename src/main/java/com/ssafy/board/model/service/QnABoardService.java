package com.ssafy.board.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.BoardParameterDto;
import com.ssafy.util.PageNavigation;

@Service
public interface QnABoardService {

	boolean writeArticle(BoardDto boardDto) throws Exception;
	List<BoardDto> listArticle(BoardParameterDto boardParameterDto) throws Exception;
	PageNavigation makePageNavigation(BoardParameterDto boardParameterDto) throws Exception;
	BoardDto getArticle(int articleno) throws Exception;
	void updateHit(int articleno) throws Exception;
	boolean modifyArticle(BoardDto boardDto) throws Exception;
	boolean deleteArticle(int articleno) throws Exception;
	
}
