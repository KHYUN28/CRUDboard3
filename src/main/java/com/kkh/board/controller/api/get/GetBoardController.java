package com.kkh.board.controller.api.get;

import com.kkh.board.model.Board;
import com.kkh.board.repository.BoardRepository;
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
public class GetBoardController {

	@Autowired
	private BoardRepository boardRepository;

	@GetMapping
	public List<Board> Boardlist() {
		return boardRepository.findAll();
	}

	@GetMapping("/{id}")
	public Board Boarddetail(@PathVariable Long id) {
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
