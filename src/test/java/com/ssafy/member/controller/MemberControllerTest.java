package com.ssafy.member.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ssafy.member.model.service.MemberService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class MemberControllerTest {

	@Autowired
	private MemberService memberService;
	
	@Test
	public void testIdCheck() {
		int count = 0;
		try {
			count = memberService
					.idCheck("ssafy");
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(1, count);
//		fail("Not yet implemented");
	}

//	@Test
//	public void testJoinMember() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testLoginMember() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testModifyMember() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testDeleteMember() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testFindpw() {
//		fail("Not yet implemented");
//	}

}
