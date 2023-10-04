package com.kkh.blog.controller.api;

import com.kkh.blog.config.auth.PrincipalDetail;
import com.kkh.blog.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.kkh.blog.dto.ReplySaveRequestDto;
import com.kkh.blog.dto.ResponseDto;
import com.kkh.blog.model.Board;

@RestController
@RequestMapping("/api/board")
public class BoardApiController {
	
	@Autowired
	private BoardService boardService;
	
	@PostMapping
	public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
		boardService.writePost(board, principal.getUser());
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@DeleteMapping("/{id}")
	public ResponseDto<Integer> deleteById(@PathVariable int id) {
		boardService.deletePost(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PutMapping("/{id}")
	public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board) {
		boardService.updatePost(id, board);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
}
