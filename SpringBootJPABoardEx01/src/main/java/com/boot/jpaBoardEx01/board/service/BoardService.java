package com.boot.jpaBoardEx01.board.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.boot.jpaBoardEx01.board.vo.Board;

public interface BoardService {

	public Page<Board> listAll(int pageNum, int pageSize);
	public void save(Board vo);
	public Board get(int board_id);
	public void delete(int id);
	public void update (Board vo);
	public void replyInsert(Board vo);
	
}
