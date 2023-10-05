package com.kkh.board.controller.api.get;

import java.util.List;

import com.kkh.board.model.Reply;
import com.kkh.board.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/replys")
public class GetReplyController {

	@Autowired
	private ReplyRepository replyRepository;

	@GetMapping
	public List<Reply> Replylist() {
		return replyRepository.findAll();
	}

	@GetMapping("/{id}")
	public Reply Replydetail(@PathVariable Long id) {
		Reply reply = replyRepository.findById(id).orElseThrow(()-> {
			return new IllegalArgumentException("해당 사용자가 없습니다.");
		});
		return reply;
	}

	@GetMapping("/reply")
	public Page<Reply> ReplypageList(@PageableDefault(size=3,sort="id",direction = Sort.Direction.DESC) Pageable pageable) {
		Page<Reply> pagingReply = replyRepository.findAll(pageable);

		List<Reply> reply = pagingReply.getContent();
		return pagingReply;
	}
}
