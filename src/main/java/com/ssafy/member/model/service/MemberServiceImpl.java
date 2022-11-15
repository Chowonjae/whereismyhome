package com.ssafy.member.model.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.dao.MemberDao;

@Service
public class MemberServiceImpl implements MemberService{
	@Autowired
	private MemberDao memberDao;
	
	@Override
	public int idCheck(String userId) throws Exception {
		int cnt = memberDao.idCheck(userId);
		return cnt;
	}

	@Override
	public int joinMember(MemberDto memberDto) throws Exception {
		String pw = memberDto.getUserPwd();
	    String hex = "";
	    
	    // "SHA1PRNG"은 알고리즘 이름
	    SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
	    byte[] bytes = new byte[16];
	    random.nextBytes(bytes);
	    
	    // SALT 생성
	    String salt = new String(Base64.getEncoder().encode(bytes));
	    String rawAndSalt = pw+salt;
	    
	    MessageDigest md = MessageDigest.getInstance("SHA-256");
	    
	    // 평문+salt 암호화
	    md.update(rawAndSalt.getBytes());
	    hex = String.format("%064x", new BigInteger(1, md.digest()));
	    System.out.println("raw+salt의 해시값 : "+hex);
	    memberDto.setSalt(salt);
	    memberDto.setUserPwd(hex);
	    memberDao.joinMember(memberDto);

		return 0;
	}

	@Override
	public MemberDto loginMember(MemberDto memberDto) throws Exception {
		String salt = memberDao.getSalt(memberDto.getUserId());
		if(salt.equals(null)) {
			return null;
		}
		String userPwd = memberDto.getUserPwd() + salt;
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		
		// 암호화
		md.update(userPwd.getBytes());
		userPwd = String.format("%064x", new BigInteger(1, md.digest()));
		
		memberDto.setUserPwd(userPwd);
		System.out.println(userPwd);
		
		// user 정보 받아오기 , 비밀번호 제외
		MemberDto userInfo = memberDao.getUserInfo(memberDto);
		System.out.println(userInfo);
		
		return userInfo;
	}

	@Override
	public int modifyMember(MemberDto memberDto) throws SQLException, NoSuchAlgorithmException {
		String pw = memberDto.getUserPwd();
	    String hex = "";
	    
	    // "SHA1PRNG"은 알고리즘 이름
	    SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
	    byte[] bytes = new byte[16];
	    random.nextBytes(bytes);
	    
	    // SALT 생성
	    String salt = new String(Base64.getEncoder().encode(bytes));
	    String rawAndSalt = pw+salt;
	    
	    MessageDigest md = MessageDigest.getInstance("SHA-256");
	    
	    // 평문+salt 암호화
	    md.update(rawAndSalt.getBytes());
	    hex = String.format("%064x", new BigInteger(1, md.digest()));
	    memberDto.setSalt(salt);
	    memberDto.setUserPwd(hex);
	    System.out.println("hi " + memberDto.toString());
		memberDao.modifyMember(memberDto);
		return 0;
	}

	@Override
	public void deleteMember(String userId) throws SQLException {
		memberDao.deleteMember(userId);
	}
	
	@Override
    public String findpw(MemberDto memberDto) throws SQLException, NoSuchAlgorithmException {
		String pw = makeRand();
		String hex = "";
		
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		byte[] bytes = new byte[16];
		random.nextBytes(bytes);
		
		String salt = new String(Base64.getEncoder().encode(bytes));
		String rawAndSalt = pw+salt;
		
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		
		md.update(rawAndSalt.getBytes());
		hex = String.format("%064x", new BigInteger(1, md.digest()));
		memberDto.setSalt(salt);
		memberDto.setUserPwd(hex);
		memberDao.findpw(memberDto);
		
		memberDao.findpw(memberDto);
        return pw;
    }
	
	/**
	  private Session
	*/
	private String makeRand() {
      Random rnd = new Random();
      StringBuilder key = new StringBuilder();
      for (int i = 0; i < 6; i++) { 
         int index = rnd.nextInt(3);
           switch (index) {
           case 0:
               key.append(((int) (rnd.nextInt(26)) + 97));
               break;
           case 1:
               key.append(((int) (rnd.nextInt(26)) + 65));
               break;
           case 2:
               key.append((rnd.nextInt(10)));
               break;
       }
      }
    return key.toString();
}
}
