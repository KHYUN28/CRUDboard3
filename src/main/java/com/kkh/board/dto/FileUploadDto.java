package com.kkh.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadDto {
    private Long userId;

    private Long boardId;

    private String orgFileName;

    private String sysFileName;

}

