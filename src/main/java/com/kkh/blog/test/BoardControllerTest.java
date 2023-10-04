package com.kkh.blog.test;

import com.kkh.blog.model.Board;
import com.kkh.blog.model.Reply;
import com.kkh.blog.model.User;
import com.kkh.blog.repository.BoardRepository;
import com.kkh.blog.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/boards")
public class BoardControllerTest {

	@Autowired
	private BoardRepository boardRepository;

	@GetMapping
	public List<Board> Boardlist() {
		return boardRepository.findAll();
	}

	@GetMapping("/{id}")
	public Board Boarddetail(@PathVariable int id) {
		Board board = boardRepository.findById(id).orElseThrow(()-> {
			return new IllegalArgumentException("해당 사용자가 없습니다.");
		});
		return board;
	}

	@GetMapping("/board")
	public Page<Board> BoardpageList(@PageableDefault(size=3,sort="id",direction = Sort.Direction.DESC) Pageable pageable) {
		Page<Board> pagingBoard = boardRepository.findAll(pageable);

		List<Board> board = pagingBoard.getContent();
		return pagingBoard;
	}
}
