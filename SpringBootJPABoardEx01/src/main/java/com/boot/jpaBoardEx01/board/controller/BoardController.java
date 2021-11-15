package com.boot.jpaBoardEx01.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.boot.jpaBoardEx01.board.service.BoardService;
import com.boot.jpaBoardEx01.board.vo.Board;

@Controller
public class BoardController {

	@Autowired
	private BoardService service;
	
	// 게시글 목록
	@RequestMapping("/{number}")
	public String main(@PathVariable("number") int number, Model model) {
		System.out.println("number : " + number);
		if(number != 0) {
			number -= 1;
		}
		Page<Board> list = service.listAll(number, 5);
		int totalPages = list.getTotalPages();
		int pageNum = list.getPageable().getPageNumber();
		int pageBlock = 3;
		// pageNum이 0부터 시작하기 때문에 +1
		int startBlockPage = ((pageNum)/pageBlock) * pageBlock + 1;
		int endBlockPage = startBlockPage + pageBlock - 1;
		endBlockPage = totalPages < endBlockPage ? totalPages : endBlockPage;
		
		model.addAttribute("startBlockPage", startBlockPage);
		model.addAttribute("endBlockPage", endBlockPage);
		model.addAttribute("list", list);
		return "main";
	}
	
	// 게시글 작성
	@RequestMapping("/new")
	public String newPost(Model model) {
		Board board = new Board();
		model.addAttribute("board", board);
		return "new_post";
	}
	
	// 게시글 작성 처리 및 수정 처리
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String saveBoard(@ModelAttribute("board") Board board) {
		service.save(board);
		return "redirect:/0";
	}
	
	// 게시글 수정 페이지
	@RequestMapping("/edit/{board_id}")
	public String editPost(@PathVariable("board_id") int board_id, Model model) {
		Board board = service.get(board_id);
		model.addAttribute("board", board);
		return "edit_page";
	}
	
	// 게시글 삭제 처리
	@RequestMapping("/delete/{board_id}")
	public String deletePost(@PathVariable("board_id") int board_id) {
		service.delete(board_id);
		return "redirect:/0";
	}
	
	// 게시글 상세조회
	@RequestMapping("/detail/{board_id}/{board_cnt}")
	public String detailPost(@PathVariable("board_id") int board_id, 
			@PathVariable("board_cnt") int board_cnt, Model model) {
		// 조회수 증가시키기
		increaseView(board_id, board_cnt);

		Board board = service.get(board_id);
		model.addAttribute("board", board);
		return "detail";
	}
	
	// 조회수 증가
	public void increaseView(int board_id, int board_cnt) {
		Board board = service.get(board_id);
		board_cnt += 1;
		board.setBoard_cnt(board_cnt);
		service.update(board);
	}
	
	// 답글 작성(신규 답글의 ref_seq는 무조건 1 ==> 가장 최상단 위치)
	@RequestMapping("/reply_post/{board_id}/{ref_seq}/{ref_level}/{ref}")
	public String replyPost(@PathVariable("board_id") int board_id,
			@PathVariable("ref_seq") int ref_seq, @PathVariable("ref") int ref,
			@PathVariable("ref_level") int ref_level, Model model) {
		Board board = new Board();
		
		if(ref_level > 0) {
			board.setRef(ref);
		} else {
			board.setRef(board_id);
		}
		board.setRef_seq(1);
		board.setRef_level(ref_level);
		model.addAttribute("board", board);
		return "reply_post";
	}
	
	@RequestMapping("/reply_insert")
	public String replyInsert(@ModelAttribute("board") Board board, Model model) {
		service.replyInsert(board);
		return "redirect:/0";
	}
	
	
}
