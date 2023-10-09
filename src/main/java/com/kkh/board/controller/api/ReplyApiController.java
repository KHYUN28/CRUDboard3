package com.kkh.board.controller.api;

import com.kkh.board.dto.RequestDto;
import com.kkh.board.dto.ResponseDto;
import com.kkh.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
public class ReplyApiController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/{boardId}/reply")
    public ResponseDto<Integer> replySave(@RequestBody RequestDto requestDto) {
        boardService.writeComment(requestDto);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/{boardId}/reply/{replyId}")
    public ResponseDto<Integer> replyDelete(@PathVariable Long replyId) {
        boardService.deleteComment(replyId);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}
