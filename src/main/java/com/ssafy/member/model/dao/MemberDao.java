package com.ssafy.member.model.dao;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.member.model.MemberDto;

@Mapper
public interface MemberDao {

	int idCheck(String userId) throws SQLException;
	int joinMember(MemberDto memberDto) throws SQLException;
//	MemberDto loginMember(MemberDto memberDto) throws SQLException, NoSuchAlgorithmException;
	void modifyMember(MemberDto memberDto) throws SQLException;
	void deleteMember(String userId) throws SQLException;
	void findpw(MemberDto memberDto) throws SQLException;
	MemberDto getUserInfo(MemberDto memberDto) throws SQLException, NoSuchAlgorithmException;
	String getSalt(String userId) throws SQLException, NoSuchAlgorithmException;
	
}
