package com.ssafy.board.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.ssafy.board.model.ReplyDto;
import com.ssafy.board.model.service.ConsonantSearcherService;
import com.ssafy.board.model.service.QnABoardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/qna")
@Api("게시판 컨트롤러  API V1")
public class QnABoardRestController {

	private static final Logger logger = LoggerFactory.getLogger(QnABoardRestController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private QnABoardService qnAboardService;
	@Autowired
	private ConsonantSearcherService searcherService;

	@ApiOperation(value = "게시판 글작성", notes = "새로운 게시글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> writeArticle(@RequestBody @ApiParam(value = "게시글 정보.", required = true) BoardDto boardDto) throws Exception {
//		boardDto.setUserId("admin");
		logger.info("writeArticle - 호출");
		if (qnAboardService.writeArticle(boardDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "게시판 글목록", notes = "모든 게시글의 정보를 반환한다.", response = List.class)
	@GetMapping
	public ResponseEntity<List<BoardDto>> listArticle(@ApiParam(value = "게시글을 얻기위한 부가정보.", required = true) BoardParameterDto boardParameterDto) throws Exception {
		logger.info("listArticle - 호출");
		return new ResponseEntity<List<BoardDto>>(qnAboardService.listArticle(boardParameterDto), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{key}/{word}")
	public ResponseEntity<?> getSearchList(@PathVariable("key") String key, @PathVariable("word") String word) throws Exception{
		BoardParameterDto boardParameterDto = new BoardParameterDto();
		boardParameterDto.setKey(key);
		boardParameterDto.setWord(word);
		List<BoardDto> list = qnAboardService.listArticle(boardParameterDto);
		String enWord = "";
		String koWord = "";
		if(list.size() == 0) {
			enWord = searcherService.engToKor(word);
			boardParameterDto.setWord(enWord);
			list = qnAboardService.listArticle(boardParameterDto);
			if(list.size() == 0) {
				koWord = searcherService.koToEn(word);
				boardParameterDto.setWord(koWord);
				list = qnAboardService.listArticle(boardParameterDto);
				if(list.size() == 0) {
					boardParameterDto.setWord(word);
					list = qnAboardService.choListArticle(boardParameterDto);
				}
			}
		}
		return new ResponseEntity<List<BoardDto>>(list, HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시판 글보기", notes = "글번호에 해당하는 게시글의 정보를 반환한다.", response = BoardDto.class)
	@GetMapping("/{articleNo}")
	public ResponseEntity<BoardDto> getArticle(@PathVariable("articleNo") @ApiParam(value = "얻어올 글의 글번호.", required = true) int articleno) throws Exception {
		logger.info("getArticle - 호출 : " + articleno);
		qnAboardService.updateHit(articleno);
		return new ResponseEntity<BoardDto>(qnAboardService.getArticle(articleno), HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시판 글수정", notes = "수정할 게시글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping
	public ResponseEntity<String> modifyArticle(@RequestBody @ApiParam(value = "수정할 글정보.", required = true) BoardDto boardDto) throws Exception {
		logger.info("modifyArticle - 호출 {}", boardDto);
		
		if (qnAboardService.modifyArticle(boardDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시판 글삭제", notes = "글번호에 해당하는 게시글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/{articleNo}")
	public ResponseEntity<String> deleteArticle(@PathVariable("articleNo") @ApiParam(value = "살제할 글의 글번호.", required = true) int articleno) throws Exception {
		logger.info("deleteArticle - 호출");
		if (qnAboardService.deleteArticle(articleno)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/repl/{articleNo}")
	public ResponseEntity<?> getReply(@PathVariable("articleNo") int articleno) throws Exception {
		logger.info("getReply - 호출 : " + articleno);
		return new ResponseEntity<List<ReplyDto>>(qnAboardService.getReply(articleno), HttpStatus.OK);
	}
	
	@PostMapping("/repl")
	public ResponseEntity<?> registReply(@RequestBody ReplyDto replyDto) throws Exception {
//		replyDto.setUser_id("admin");
		logger.info("registReply - 호출 : " + replyDto.toString());
		if(qnAboardService.registReply(replyDto)) {
			return new ResponseEntity<List<ReplyDto>>(qnAboardService.getReply(replyDto.getArticle_no()), HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	
	@ApiOperation(value = "게시판 글수정", notes = "수정할 게시글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/repl")
	public ResponseEntity<?> modifyReply(@RequestBody @ApiParam(value = "수정할 글정보.", required = true) ReplyDto replyDto) throws Exception {
		logger.info("modifyReply - 호출 {}", replyDto);
		
		if (qnAboardService.modifyReply(replyDto)) {
			return new ResponseEntity<List<ReplyDto>>(qnAboardService.getReply(replyDto.getArticle_no()), HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}
	
	@ApiOperation(value = "댓글삭제", notes = "글번호에 해당하는 게시글의 해당하는 댓글를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/repl/{articleNo}/{replyNo}")
	public ResponseEntity<?> deleteReply(@PathVariable("articleNo") @ApiParam(value = "살제할 댓글의 글번호.", required = true) int articleno,
			@PathVariable("replyNo") @ApiParam(value = "살제할 글의 댓글번호.", required = true) int replyNo) throws Exception {
		logger.info("deleteReply - 호출");
		System.out.println(articleno+" "+replyNo);
		if (qnAboardService.deleteReply(replyNo)) {
			return new ResponseEntity<List<ReplyDto>>(qnAboardService.getReply(articleno), HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

}
