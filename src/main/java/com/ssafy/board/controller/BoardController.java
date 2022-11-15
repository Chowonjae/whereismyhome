//package com.ssafy.board.controller;
//
//import java.io.File;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.ServletContext;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.ResourceLoader;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import com.ssafy.board.model.BoardDto;
//import com.ssafy.board.model.FileInfoDto;
//import com.ssafy.board.model.service.BoardService;
//import com.ssafy.member.model.MemberDto;
//import com.ssafy.util.PageNavigation;
//
//@Controller
//@RequestMapping("/board")
//public class BoardController {
//
//	@Autowired
//	ResourceLoader resLoader;
//	@Autowired
//	private ServletContext servletContext;
//	@Autowired
//	private BoardService boardService;
//	
//	@GetMapping("/write")
//	public String write(@RequestParam Map<String, String> map, Model model) {
//		model.addAttribute("pgno", map.get("pgno"));
//		model.addAttribute("key", map.get("key"));
//		model.addAttribute("word", map.get("word"));
//		return "board/write";
//	}
//
//	@PostMapping("/write")
//	public String write(@Value("${file.path.upload-files}") String filePath, BoardDto boardDto, @RequestParam("upfile") MultipartFile[] files, HttpSession session,
//			RedirectAttributes redirectAttributes) throws Exception {
//		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
//		boardDto.setUserId(memberDto.getUserId());
////		filePath = servletContext.getRealPath("/upload");
////		FileUpload 관련 설정.
//		if (files.length > 0) {
//			String today = new SimpleDateFormat("yyMMdd").format(new Date());
//			String saveFolder = filePath + File.separator + today;
//			File folder = new File(saveFolder);
//			if (!folder.exists())
//				folder.mkdirs();
//			List<FileInfoDto> fileInfos = new ArrayList<FileInfoDto>();
//			for (MultipartFile mfile : files) {
//				FileInfoDto fileInfoDto = new FileInfoDto();
//				String originalFileName = mfile.getOriginalFilename();
//				if (!originalFileName.isEmpty()) {
//					String saveFileName = System.nanoTime()
//							+ originalFileName.substring(originalFileName.lastIndexOf('.'));
//					fileInfoDto.setSaveFolder(today);
//					fileInfoDto.setOriginalFile(originalFileName);
//					fileInfoDto.setSaveFile(saveFileName);
//					mfile.transferTo(new File(folder, saveFileName));
//				}
//				fileInfos.add(fileInfoDto);
//			}
//			boardDto.setFileInfos(fileInfos);
//		}
//		boardService.writeArticle(boardDto);
//		redirectAttributes.addAttribute("pgno", "1");
//		redirectAttributes.addAttribute("key", "");
//		redirectAttributes.addAttribute("word", "");
//		return "redirect:/board/list";
//	}
//
//	@GetMapping("/list")
//	public ModelAndView list(@RequestParam Map<String, String> map) throws Exception {
//		ModelAndView mav = new ModelAndView();
//		List<BoardDto> list = boardService.listArticle(map);
//		
//		String word = map.get("word");
//		String enWord = "";
//		String koWord = "";
//		
//		if(list.size()==0) {
//			enWord = boardService.engToKor(word);
//			map.put("word", enWord);
//			list = boardService.listArticle(map);
//			
//			if(list.size()==0) {
//				koWord = boardService.koToEn(word);
//				map.put("word", koWord);
//				list = boardService.listArticle(map);
//				if(list.size()==0) {
//					list = boardService.choListArticle(map);
//				}
//			}
//		}
//		
//		PageNavigation pageNavigation = boardService.makePageNavigation(map);
//		mav.addObject("articles", list);
//		mav.addObject("navigation", pageNavigation);
//		mav.addObject("pgno", map.get("pgno"));
//		mav.addObject("key", map.get("key"));
//		mav.addObject("word", map.get("word"));
//		mav.setViewName("board/list");
//		return mav;
//	}
//
//	@GetMapping("/view")
//	public String view(@RequestParam("articleno") int articleNo, @RequestParam Map<String, String> map, Model model)
//			throws Exception {
//		BoardDto boardDto = boardService.getArticle(articleNo);
//		boardService.updateHit(articleNo);
//		model.addAttribute("article", boardDto);
//		model.addAttribute("pgno", map.get("pgno"));
//		model.addAttribute("key", map.get("key"));
//		model.addAttribute("word", map.get("word"));
//		return "board/view";
//	}
//
//	@GetMapping("/modify")
//	public String modify(@RequestParam("articleno") int articleNo, @RequestParam Map<String, String> map, Model model)
//			throws Exception {
//		BoardDto boardDto = boardService.getArticle(articleNo);
//		model.addAttribute("article", boardDto);
//		model.addAttribute("pgno", map.get("pgno"));
//		model.addAttribute("key", map.get("key"));
//		model.addAttribute("word", map.get("word"));
//		return "board/modify";
//	}
//
//	@PostMapping("/modify")
//	public String modify(BoardDto boardDto, @RequestParam Map<String, String> map,
//			RedirectAttributes redirectAttributes) throws Exception {
//		System.out.println(boardDto.toString());
//		boardService.modifyArticle(boardDto);
//		redirectAttributes.addAttribute("pgno", map.get("pgno"));
//		redirectAttributes.addAttribute("key", map.get("key"));
//		redirectAttributes.addAttribute("word", map.get("word"));
//		return "redirect:/board/list";
//	}
//
//	@GetMapping("/delete")
//	public String delete(@RequestParam("articleno") int articleNo, @RequestParam Map<String, String> map,
//			RedirectAttributes redirectAttributes) throws Exception {
//		System.out.println(servletContext.getRealPath("/upload"));
//		boardService.deleteArticle(articleNo, servletContext.getRealPath("/upload"));
//		redirectAttributes.addAttribute("pgno", map.get("pgno"));
//		redirectAttributes.addAttribute("key", map.get("key"));
//		redirectAttributes.addAttribute("word", map.get("word"));
//		return "redirect:/board/list";
//	}
//
//	@GetMapping(value = "/download")
//	public ModelAndView downloadFile(@RequestParam("sfolder") String sfolder, @RequestParam("ofile") String ofile,
//			@RequestParam("sfile") String sfile, HttpSession session) {
//		MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
//		if (memberDto != null) {
//			Map<String, Object> fileInfo = new HashMap<String, Object>();
//			fileInfo.put("sfolder", sfolder);
//			fileInfo.put("ofile", ofile);
//			fileInfo.put("sfile", sfile);
//
//			return new ModelAndView("fileDownLoadView", "downloadFile", fileInfo);
//		} else {
//			return new ModelAndView("redirect:/");
//		}
//	}
//}
