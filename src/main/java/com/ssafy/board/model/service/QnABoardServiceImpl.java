package com.ssafy.board.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.BoardParameterDto;
import com.ssafy.board.model.ReplyDto;
import com.ssafy.board.model.dao.QnABoardDao;
import com.ssafy.util.PageNavigation;

@Service
public class QnABoardServiceImpl implements QnABoardService {

	@Autowired
	private QnABoardDao qnAboardDao;
	@Autowired
	private ConsonantSearcherService consonantSearcherService;

	@Override
	public boolean writeArticle(BoardDto boardDto) throws Exception {
		if(boardDto.getSubject() == null || boardDto.getContent() == null) {
			throw new Exception();
		}
		return qnAboardDao.writeArticle(boardDto) == 1;
	}

	@Override
	public List<BoardDto> listArticle(BoardParameterDto boardParameterDto) throws Exception {
		int start = boardParameterDto.getPg() == 0 ? 0 : (boardParameterDto.getPg() - 1) * boardParameterDto.getSpp();
		boardParameterDto.setStart(start);
		List<BoardDto> temp = qnAboardDao.listArticle(boardParameterDto);
		return temp;
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
			List<BoardDto> getSoundSearcher = qnAboardDao.soundSearcherListArticle(boardParameterDto);
			List<BoardDto> result = new ArrayList<>();
			for(BoardDto bDto : getSoundSearcher) {
				String title = bDto.getSubject();
				if(consonantSearcherService.matchString(title, cho)) {
					result.add(bDto);
				}
			}
			return result;
		}else {	// 일반검색
			return qnAboardDao.listArticle(boardParameterDto);
		}
	}
	
	@Override
	public List<ReplyDto> getReply(int articleno) throws Exception {
		return qnAboardDao.getReply(articleno);
	}
	
	@Override
	public boolean registReply(ReplyDto replyDto) throws Exception {
		if(replyDto.getComment() == null) {
			throw new Exception();
		}
		System.out.println(replyDto.getComment());
		return qnAboardDao.registReply(replyDto) == 1;
	}

	@Override
	public PageNavigation makePageNavigation(BoardParameterDto boardParameterDto) throws Exception {
		int naviSize = 5;
		PageNavigation pageNavigation = new PageNavigation();
		pageNavigation.setCurrentPage(boardParameterDto.getPg());
		pageNavigation.setNaviSize(naviSize);
		int totalCount = qnAboardDao.getTotalCount(boardParameterDto);//총글갯수  269
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
	public BoardDto getArticle(int articleno) throws Exception {
		return qnAboardDao.getArticle(articleno);
	}
	
	@Override
	public void updateHit(int articleno) throws Exception {
		qnAboardDao.updateHit(articleno);
	}

	@Override
	@Transactional
	public boolean modifyArticle(BoardDto boardDto) throws Exception {
		return qnAboardDao.modifyArticle(boardDto) == 1;
	}

	@Override
	@Transactional
	public boolean deleteArticle(int articleno) throws Exception {
		qnAboardDao.deleteMemo(articleno);
		return qnAboardDao.deleteArticle(articleno) == 1;
	}


	
}
