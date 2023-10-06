package com.kkh.board.controller.api;

import com.kkh.board.config.auth.PrincipalDetail;
import com.kkh.board.model.File;
import com.kkh.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.kkh.board.dto.ResponseDto;
import com.kkh.board.model.Board;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.nio.file.Paths;

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
	public ResponseDto<Integer> deleteById(@PathVariable Long id) {
		boardService.deletePost(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PutMapping("/{id}")
	public ResponseDto<Integer> update(@PathVariable Long id, @RequestBody Board board) {
		boardService.updatePost(id, board);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> uploadFile(
			@RequestParam("uploadfile") MultipartFile uploadfile) {

		try {

			String filename = uploadfile.getOriginalFilename();
			String directory = "/var/netgloo_blog/uploads";
			String filepath = Paths.get(directory, filename).toString();

			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(String.valueOf(new File(filepath))));
			stream.write(uploadfile.getBytes());
			stream.close();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}
}