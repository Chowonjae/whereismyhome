package com.ssafy.member.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.service.JwtServiceImpl;
import com.ssafy.member.model.service.MemberService;

@RestController
@RequestMapping("/rmember")
@CrossOrigin("*")
public class MemberRestController {
	
	public static final Logger logger = LoggerFactory.getLogger(MemberRestController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private JwtServiceImpl jwtService;
	@Autowired
	private MemberService memberService;
	
	// 아이디 중복 확인
	@GetMapping(value = "/{userid}")
	public ResponseEntity<?> searchId(@PathVariable("userid") String userId){
		try {
			int checkCnt = memberService.idCheck(userId);
			String state = "ready";
			if(checkCnt > 0) state = "already";
			return new ResponseEntity<String>(state, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// 비밀번호 찾기
	@PostMapping(value = "/findpw")
	public ResponseEntity<?> findpw(@RequestBody MemberDto memberDto){
		try {
			String result = memberService.findpw(memberDto);
			System.out.println("RESULT : " + result);
			if(result.equals(SUCCESS)) {
				return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}else {
				return new ResponseEntity<String>(FAIL, HttpStatus.OK);
			}
		}catch(Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// 회원가입
	@PostMapping
	public ResponseEntity<?> memberRegister(@RequestBody MemberDto memberDto){
		try {
			System.out.println(memberDto.toString());
			memberService.joinMember(memberDto);
			return new ResponseEntity<String>(SUCCESS, HttpStatus.CREATED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	// jwt
	// 로그인
	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody MemberDto memberDto){
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			MemberDto loginUser = memberService.loginMember(memberDto);
			if (loginUser != null) {
				String accessToken = jwtService.createAccessToken("userid", loginUser.getUserId());// key, data
				String refreshToken = jwtService.createRefreshToken("userid", loginUser.getUserId());// key, data
				memberService.saveRefreshToken(memberDto.getUserId(), refreshToken);
				logger.debug("로그인 accessToken 정보 : {}", accessToken);
				logger.debug("로그인 refreshToken 정보 : {}", refreshToken);
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			return exceptionHandling(e);
		}
		System.out.println(resultMap.toString());
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	// 회원 인증, 회원 정보를 담은 token을 반환
	@GetMapping("/info/{userid}")
	public ResponseEntity<?> getInfo(
			@PathVariable("userid") String userid,
			HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		if (jwtService.checkToken(request.getHeader("access-token"))) {
			logger.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				MemberDto memberDto = memberService.userInfo(userid);
				logger.debug("memberDto : ", memberDto.toString());
				resultMap.put("userInfo", memberDto);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				return exceptionHandling(e);
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	// 로그아웃
	@GetMapping("/logout/{userid}")
	public ResponseEntity<?> removeToken(@PathVariable("userid") String userid) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			memberService.deleRefreshToken(userid);
			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("로그아웃 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			return exceptionHandling(e);
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	// 갖고 있는 refreshToken으로 accessToken 재발급
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody MemberDto memberDto, HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refresh-token");
		logger.debug("token : {}, memberDto : {}", token, memberDto);
		if (jwtService.checkToken(token)) {
			if (token.equals(memberService.getRefreshToken(memberDto.getUserId()))) {
				String accessToken = jwtService.createAccessToken("userid", memberDto.getUserId());
				logger.debug("token : {}", accessToken);
				logger.debug("정상적으로 액세스토큰 재발급!!!");
				resultMap.put("access-token", accessToken);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			}
		} else {
			logger.debug("리프레쉬토큰도 사용불!!!!!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@PutMapping("/mypage")
	public ResponseEntity<?> updateUser(@RequestBody MemberDto memberDto) {
		try {
			memberService.modifyMember(memberDto);
			return new ResponseEntity<String>(SUCCESS, HttpStatus.CREATED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@Transactional
	@DeleteMapping("/{userid}")
	public ResponseEntity<?> delete(@PathVariable("userid") String userid){
		System.out.println(12333123);
		System.out.println(userid);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			memberService.deleteMember(userid);
			memberService.deleRefreshToken(userid);;
			resultMap.put("message", SUCCESS);
		}catch (Exception e) {
			resultMap.put("message", e.getMessage());
			return exceptionHandling(e);
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	//api
	
	// 회원가입
	@Transactional
	@PostMapping("/kakao")
	public ResponseEntity<?> apiJoinMember(@RequestBody MemberDto memberDto){
		System.out.println(memberDto);
		MemberDto user;
		try {
			user = memberService.userInfo(memberDto.getUserId());
			if(user==null) {
				memberService.ApiJoinMember(memberDto);				
				user = memberService.userInfo(memberDto.getUserId());
			}
			//String refreshToken = jwtService.createRefreshToken("userid", memberDto.getUserId());// key, data
			//memberService.saveRefreshToken(memberDto.getUserId(), refreshToken);
			System.out.println(user);
			return new ResponseEntity<MemberDto>(user, HttpStatus.CREATED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
