package com.ssafy.board.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.BoardParameterDto;
import com.ssafy.board.model.service.BoardService;
import com.ssafy.board.model.service.ConsonantSearcherService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/board")
@CrossOrigin("*")
@Api("게시판 컨트롤러  API V1")
public class BoardRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardRestController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private BoardService boardService;
	@Autowired
	private ConsonantSearcherService searcherService;
	
	@ApiOperation(value = "게시판 글목록", notes = "모든 게시글의 정보를 반환한다.", response = List.class)
	@GetMapping
	public ResponseEntity<?> getList(@ApiParam(value = "게시글을 얻기위한 부가정보.", required = true) BoardParameterDto boardParameterDto) throws Exception{
		logger.info("listArticle - 호출");
		return new ResponseEntity<List<BoardDto>>(boardService.listArticle(boardParameterDto), HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시판 글 상세 보기", notes = "글번호에 해당하는 게시글의 정보를 반환한다.", response = BoardDto.class)
	@GetMapping(value = "/{articleNo}")
	public ResponseEntity<BoardDto> getArticle(@PathVariable("articleNo") @ApiParam(value = "얻어올 글의 글번호.", required = true) int articleno) throws Exception{
		logger.info("getArticle - 호출 : " + articleno);
		boardService.updateHit(articleno);
		return new ResponseEntity<BoardDto>(boardService.getArticle(articleno), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{key}/{word}")
	public ResponseEntity<?> getSearchList(@PathVariable("key") String key, @PathVariable("word") String word) throws Exception{
		BoardParameterDto boardParameterDto = new BoardParameterDto();
		boardParameterDto.setKey(key);
		boardParameterDto.setWord(word);
		System.out.println(boardParameterDto.toString());
		List<BoardDto> list = boardService.listArticle(boardParameterDto);
		String enWord = "";
		String koWord = "";
		if(list.size() == 0) {
			enWord = searcherService.engToKor(word);
			boardParameterDto.setWord(enWord);
			list = boardService.listArticle(boardParameterDto);
			if(list.size() == 0) {
				koWord = searcherService.koToEn(word);
				boardParameterDto.setWord(koWord);
				list = boardService.listArticle(boardParameterDto);
				if(list.size() == 0) {
					boardParameterDto.setWord(word);
					list = boardService.choListArticle(boardParameterDto);
				}
			}
		}
		return new ResponseEntity<List<BoardDto>>(list, HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시판 글작성", notes = "새로운 게시글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<?> writeArticle(@RequestBody @ApiParam(value = "게시글 정보.", required = true) BoardDto boardDto) throws Exception{
		logger.info("writeArticle - 호출");
		if(boardService.writeArticle(boardDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "게시판 글수정", notes = "수정할 게시글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping
	public ResponseEntity<?> modifyArticle(@RequestBody @ApiParam(value = "수정할 글정보.", required = true) BoardDto boardDto) throws Exception{
		logger.info("modifyArticle - 호출 {}", boardDto);
		if(boardService.modifyArticle(boardDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시판 글삭제", notes = "글번호에 해당하는 게시글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/{articleNo}")
	public ResponseEntity<?> deleteArticle(@PathVariable("articleNo") @ApiParam(value = "살제할 글의 글번호.", required = true) int articleno) throws Exception{
		if(boardService.deleteArticle(articleno)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}
