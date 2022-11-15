package com.ssafy.board.model.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.BoardParameterDto;
import com.ssafy.board.model.FileInfoDto;

@Mapper
public interface BoardDao {

	int writeArticle(BoardDto boardDto) throws SQLException;
	void registerFile(BoardDto boardDto) throws Exception;
	List<BoardDto> listArticle(BoardParameterDto boardParameterDto) throws SQLException;
	int getTotalCount(BoardParameterDto boardParameterDto) throws SQLException;
	BoardDto getArticle(int articleNo) throws SQLException;
	void updateHit(int articleNo) throws SQLException;
	int modifyArticle(BoardDto boardDto) throws SQLException;
	int deleteArticle(int articleNo) throws SQLException;
	void deleteFile(int articleNo) throws Exception;
	List<BoardDto> soundSearcherListArticle(BoardParameterDto boardParameterDto) throws SQLException;
	List<FileInfoDto> fileInfoList(int articleNo) throws Exception;
	void deleteMemo(int articleNo);
	
}
