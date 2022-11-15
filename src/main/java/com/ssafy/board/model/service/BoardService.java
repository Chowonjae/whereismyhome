package com.ssafy.board.model.service;

import java.util.List;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.BoardParameterDto;
import com.ssafy.util.PageNavigation;

public interface BoardService {

	boolean writeArticle(BoardDto boardDto) throws Exception;
	List<BoardDto> listArticle(BoardParameterDto boardParameterDto) throws Exception;
	PageNavigation makePageNavigation(BoardParameterDto boardParameterDto) throws Exception;
	BoardDto getArticle(int articleNo) throws Exception;
	void updateHit(int articleNo) throws Exception;
	boolean modifyArticle(BoardDto boardDto) throws Exception;
	boolean deleteArticle(int articleNo) throws Exception;
	
	List<BoardDto> choListArticle(BoardParameterDto boardParameterDto) throws Exception;
	
}
