package com.ssafy.board.model.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.BoardParameterDto;
import com.ssafy.board.model.ReplyDto;

@Repository
public interface QnABoardDao {

	int writeArticle(BoardDto boardDto) throws SQLException;
	List<BoardDto> listArticle(BoardParameterDto boardParameterDto) throws SQLException;
	int getTotalCount(BoardParameterDto boardParameterDto) throws SQLException;
	BoardDto getArticle(int articleno) throws SQLException;
	void updateHit(int articleno) throws SQLException;
	int modifyArticle(BoardDto boardDto) throws SQLException;
	void deleteMemo(int articleno) throws SQLException;
	List<BoardDto> soundSearcherListArticle(BoardParameterDto boardParameterDto) throws SQLException;
	int deleteArticle(int articleno) throws SQLException;
	List<ReplyDto> getReply(int articleno);
	int registReply(ReplyDto replyDto);
	
}
