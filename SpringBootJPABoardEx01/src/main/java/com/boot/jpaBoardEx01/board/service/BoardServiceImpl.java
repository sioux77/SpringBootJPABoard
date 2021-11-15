package com.boot.jpaBoardEx01.board.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.boot.jpaBoardEx01.board.persistence.BoardRepository;
import com.boot.jpaBoardEx01.board.vo.Board;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired 
	private BoardRepository dao;

	@Override
	public Page<Board> listAll(int pageNum, int pageSize) {
		Sort sort = Sort.by(
			    Sort.Order.desc("ref")
			    );
		PageRequest pageRequest = PageRequest.of(pageNum, pageSize, sort);
					
		return dao.findAll(pageRequest);
	}

	@Override
	public void save(Board vo) {
		vo.setBoard_reg_date(new Date());
		dao.save(vo);
	}
	
	@Override
	public void update(Board vo) {
		dao.save(vo);
	}
	
	@Override
	public Board get(int board_id) {
		return dao.findById(board_id).get();
	}

	@Override
	public void delete(int board_id) {
		dao.deleteById(board_id);
	}

	// 답글 삽입
	@Override
	public void replyInsert(Board vo) {
		dao.updateRefSeqs(vo.getRef(), vo.getRef_level());
		save(vo);
	}

	
	
}
