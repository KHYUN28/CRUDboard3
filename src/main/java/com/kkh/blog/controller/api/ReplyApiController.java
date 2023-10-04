package com.kkh.blog.controller.api;

import com.kkh.blog.dto.ReplySaveRequestDto;
import com.kkh.blog.dto.ResponseDto;
import com.kkh.blog.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
public class ReplyApiController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/{boardId}/reply")
    public ResponseDto<Integer> replySave(@RequestBody ReplySaveRequestDto replySaveRequestDto) {
        boardService.writeComment(replySaveRequestDto);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/{boardId}/reply/{replyId}")
    public ResponseDto<Integer> replyDelete(@PathVariable int replyId) {
        boardService.deleteComment(replyId);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}
