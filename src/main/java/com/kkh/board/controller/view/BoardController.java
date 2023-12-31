package com.kkh.board.controller.view;

import com.kkh.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;

	@GetMapping({"", "/"})
	public String index(Model model, @PageableDefault(size=5,sort="id",direction = Sort.Direction.DESC) Pageable pageable) {
		model.addAttribute("boards", boardService.getPostList(pageable));
		return "index";
	}
	
	@GetMapping("/board/{id}")
	public String findById(@PathVariable Long id, Model model) {
		model.addAttribute("board", boardService.getPostDetail(id));
		return "board/detail";
	}
	
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable Long id, Model model) {
		model.addAttribute("board", boardService.getPostDetail(id));
		return "board/updateForm";
	}

	@GetMapping({"/board/saveForm"})
	public String saveForm() {
		return "board/saveForm";
	}

}
