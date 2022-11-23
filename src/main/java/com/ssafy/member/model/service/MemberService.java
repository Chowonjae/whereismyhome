package com.ssafy.member.model.service;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import com.ssafy.member.model.MemberDto;

public interface MemberService {

	int idCheck(String userId) throws Exception; //아이디 중복검사
	int joinMember(MemberDto memberDto) throws Exception; //회원가입
	MemberDto loginMember(MemberDto memberDto) throws Exception; //로그인
	int modifyMember(MemberDto memberDto) throws SQLException, NoSuchAlgorithmException;
	void deleteMember(String userId) throws SQLException;
	String findpw(MemberDto memberDto) throws SQLException, NoSuchAlgorithmException;
	
	// jwt
	MemberDto userInfo(String userid) throws Exception;
	void saveRefreshToken(String userid, String refreshToken) throws Exception;
	Object getRefreshToken(String userid) throws Exception;
	void deleRefreshToken(String userid) throws Exception;
	
	//api
	int ApiJoinMember(MemberDto memberDto) throws Exception;
}
