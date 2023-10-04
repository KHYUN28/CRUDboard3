package com.kkh.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kkh.blog.model.Board;

public interface BoardRepository extends JpaRepository<Board, Integer>{

}