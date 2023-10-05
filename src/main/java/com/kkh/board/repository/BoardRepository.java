package com.kkh.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kkh.board.model.Board;

public interface BoardRepository extends JpaRepository<Board, Integer>{

}