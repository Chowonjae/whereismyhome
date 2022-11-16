//package com.ssafy.member.controller;
//
//import java.sql.SQLException;
//import java.util.Map;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.ssafy.member.model.MemberDto;
//import com.ssafy.member.model.service.MemberService;
//
//@Controller
//@RequestMapping("/user")
//public class MemberController {
//	
//	@Autowired
//	private MemberService memberService;
//	
//	/** 페이징 */
//	@GetMapping("/mvjoin")
//	public String mvjoin() throws Exception{
//		return "user/join";
//	}
//	
//	@GetMapping("/mvfindpw")
//	public String mvfindpw() throws Exception{
//		return "user/findpw";
//	}
//	
//	@GetMapping("/mvmypage")
//	public String mvmypage() throws Exception{
//		return "user/mypage";
//	}
//	
//	@GetMapping("/mvmypagemodify")
//	public String mvmypagemodify() throws Exception{
//		return "user/mypagemodify";
//	}
//	
//	/** 로직 */
//	@PostMapping("/findpw")
//	public String findpw(MemberDto memberDto, Model model) throws Exception{
//		model.addAttribute("msg2", memberDto.getUserName() + "님의 임시 비밀번호가 '" + memberService.findpw(memberDto) + "' 로 설정되었습니다.");
//		return "result/findpw";
//	}
//	
//	@PostMapping("/modifyuser")
//	public String modifyuser(MemberDto memberDto, HttpSession session, Model model) throws Exception{
//		System.out.println("hello " + memberDto.toString());
//		memberService.modifyMember(memberDto);
//		session.invalidate();
//		return "redirect:/";
//	}
//	
//	@PostMapping("/join")
//	public String join(MemberDto memberDto, Model model) {
//		try {
//			memberService.joinMember(memberDto);
//			model.addAttribute("msg", memberDto.getUserName() + "님 환영합니다.");
//			return "redirect:/";
//		} catch (Exception e) {
//			e.printStackTrace();
//			model.addAttribute("msg", "회원 가입 중 문제 발생!");
//			return "error/error";
//		}
//	}
//	
//	@PostMapping("/login")
//	public String login(@RequestParam Map<String, String> map, Model model, HttpSession session, HttpServletResponse response) {
//		MemberDto memberDto = new MemberDto();
//		memberDto.setUserId(map.get("userid"));
//		memberDto.setUserPwd(map.get("userpwd"));
//		try {
//			MemberDto result = memberService.loginMember(memberDto);
//			if(result != null) {
//				session.setAttribute("userinfo", result);
//				
//				Cookie cookie = new Cookie("ssafy_id", map.get("userid"));
//				cookie.setPath("/");
//				if("ok".equals(map.get("saveid"))) {
//					cookie.setMaxAge(60*60*24*365*40);
//				} else {
//					cookie.setMaxAge(0);
//				}
//				response.addCookie(cookie);
//				return "redirect:/";
//			}else {
//				model.addAttribute("msg", "아이디 또는 비밀번호 확인 후 다시 로그인하세요!");
//				System.out.println("여기로?");
//				return "redirect:/";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			model.addAttribute("msg", "로그인 중 문제 발생!!!");
//			return "error/error";
//		}
//	}
//	
//	@GetMapping("/delete")
//	public String delete(HttpSession session, HttpServletResponse response, Model model) throws SQLException {
//		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
//		memberService.deleteMember(memberDto.getUserId());
//		Cookie cookie = new Cookie("ssafy_id", memberDto.getUserId());
//		cookie.setPath("/");
//		cookie.setMaxAge(0);
//		response.addCookie(cookie);
//		session.invalidate();
//		return "result/delete";
//	}
//	
//	@GetMapping("/logout")
//	public String logout(HttpSession session) {
//		session.invalidate();
//		return "redirect:/";
//	}
//	
//}
