package com.ssafy.board.model.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.BoardParameterDto;
import com.ssafy.board.model.FileInfoDto;
import com.ssafy.board.model.dao.BoardDao;
import com.ssafy.util.PageNavigation;

@Service
public class BoardServiceImpl implements BoardService {//여기서 무엇을 하느냐?
	
	@Autowired
	private BoardDao boardDao;
	@Autowired
	private ConsonantSearcherService consonantSearcherService;
	
	@Override
	public boolean writeArticle(BoardDto boardDto) throws Exception {
		if(boardDto.getSubject() == null || boardDto.getContent() == null) {
			throw new Exception();
		}
		return boardDao.writeArticle(boardDto) == 1;
	}

	@Override
	public List<BoardDto> listArticle(BoardParameterDto boardParameterDto) throws Exception {
		int start = boardParameterDto.getPg() == 0 ? 0 : (boardParameterDto.getPg() - 1) * boardParameterDto.getSpp();
		boardParameterDto.setStart(start);
		return boardDao.listArticle(boardParameterDto);
	}
	
	@Override
	public PageNavigation makePageNavigation(BoardParameterDto boardParameterDto) throws Exception {
		int naviSize = 5;
		PageNavigation pageNavigation = new PageNavigation();
		pageNavigation.setCurrentPage(boardParameterDto.getPg());
		pageNavigation.setNaviSize(naviSize);
		int totalCount = boardDao.getTotalCount(boardParameterDto);//총글갯수  269
		pageNavigation.setTotalCount(totalCount);  
		int totalPageCount = (totalCount - 1) / boardParameterDto.getSpp() + 1;//27
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = boardParameterDto.getPg() <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < boardParameterDto.getPg();
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();
		return pageNavigation;
	}
	
	@Override
	public List<BoardDto> choListArticle(BoardParameterDto boardParameterDto) throws Exception {
		String cho = boardParameterDto.getWord();
		
		boolean flag = false;	// 초성인지 아닌지 판단
		for(char c : cho.toCharArray()) {
			if(consonantSearcherService.isInitialSound(c)) {	// true면 초성, 하나라도 초성이 있으면 초성 검색
				flag = true;
				break;
			}
		}
		
		if(flag) {
			List<BoardDto> getSoundSearcher = boardDao.soundSearcherListArticle(boardParameterDto);
			List<BoardDto> result = new ArrayList<>();
			for(BoardDto bDto : getSoundSearcher) {
				String title = bDto.getSubject();
				if(consonantSearcherService.matchString(title, cho)) {
					result.add(bDto);
				}
			}
			return result;
		}else {	// 일반검색
			return boardDao.listArticle(boardParameterDto);
		}
	}

	@Override
	public BoardDto getArticle(int articleNo) throws Exception {
		return boardDao.getArticle(articleNo);
	}
	

	@Override
	public void updateHit(int articleNo) throws Exception {
		boardDao.updateHit(articleNo);
	}

	@Override
	public boolean modifyArticle(BoardDto boardDto) throws Exception {
		return boardDao.modifyArticle(boardDto) == 1;
	}

	@Override
	public boolean deleteArticle(int articleNo) throws Exception {
		List<FileInfoDto> fileList = boardDao.fileInfoList(articleNo);
		boardDao.deleteFile(articleNo);
//		boardDao.deleteMemo(articleNo);
		for(FileInfoDto fileInfoDto : fileList) {
//			File file = new File(path + File.separator + fileInfoDto.getSaveFolder() + File.separator + fileInfoDto.getSaveFile());
//			file.delete();
		}
		return boardDao.deleteArticle(articleNo) == 1;
	}

}
