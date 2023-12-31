package com.kkh.board.service;

import com.kkh.board.repository.BoardRepository;
import com.kkh.board.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kkh.board.dto.RequestDto;
import com.kkh.board.model.Board;
import com.kkh.board.model.User;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	@Autowired
	private ReplyRepository replyRepository;
	
	@Transactional
	public void writePost(Board board, User user) {
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	
	@Transactional(readOnly = true)
	public Page<Board> getPostList(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Board getPostDetail(Long id) {
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
				});
	}
	
	@Transactional
	public void deletePost(Long id) {
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void updatePost(Long id, Board requestBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
				});
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
	}

	@Transactional
	public void writeComment(RequestDto requestDto) {
		int result = replyRepository.mSave(requestDto.getUserId(), requestDto.getBoardId(), requestDto.getContent());
		System.out.println("boardService: " + result);
	}

	@Transactional
	public void deleteComment(Long replyId) {
		replyRepository.deleteById(replyId);
	}
}
