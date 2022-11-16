package com.ssafy.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.service.MemberService;

@RestController
@RequestMapping("/rmember")
@CrossOrigin("*")
public class MemberRestController {
	
	@Autowired
	private MemberService memberService;
	
	// 아이디 중복 확인
	@GetMapping(value = "/user/{userid}")
	public ResponseEntity<?> searchId(@PathVariable("userid") String userId){
		try {
			int checkCnt = memberService.idCheck(userId);
			return new ResponseEntity<Integer>(checkCnt, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// 비밀번호 찾기
	@PostMapping(value = "/user/findpw")
	public ResponseEntity<?> findpw(@RequestBody MemberDto memberDto){
		try {
			String tempPw = memberService.findpw(memberDto);
			return new ResponseEntity<String>(tempPw, HttpStatus.OK);
		}catch(Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// 회원가입
	@PostMapping(value = "/user")
	public ResponseEntity<?> memberRegister(@RequestBody MemberDto memberDto){
		try {
			memberService.joinMember(memberDto);
			return new ResponseEntity<String>("200", HttpStatus.CREATED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// 로그인
	@PostMapping(value = "/user/login")
	public ResponseEntity<?> login(@RequestBody MemberDto memberDto){
		try {
			if(memberDto != null) {
				MemberDto temp = memberService.loginMember(memberDto);
				System.out.println(temp.toString());
				return new ResponseEntity<MemberDto>(temp, HttpStatus.OK);
			}
		}catch(Exception e) {
			return exceptionHandling(e);
		}
		return null;
	}
	
//	@DeleteMapping("/user/delete")
//	public ResponseEntity<?> delete()
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
