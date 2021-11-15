package com.boot.jpaBoardEx01.board.persistence;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.boot.jpaBoardEx01.board.vo.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE Board SET ref_seq = ref_seq + 1 WHERE ref = :ref AND ref_level = :ref_level")
	void updateRefSeqs(int ref, int ref_level);
	
	// 쿼리를 날리는 방법 2가지
	// 1. JPQL : SQL을 추상화한 객체지향쿼리 언어이다. ==> @Entity를 가진 클래스명을 그대로 사용해야함 (대소문자 구별) 
	//										==> UPDATE Board SET : 물리적 테이블은 board지만 클래스명이 Board임
    //		     nativeQuery = false (default)
	// 2. SQL : 일반 sql문. 평소와 똑같이 쓰되, natvieQuery = true 설정이 필요함
	
}
