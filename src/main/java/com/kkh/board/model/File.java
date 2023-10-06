package com.kkh.board.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Entity
@NoArgsConstructor
@Getter
public class File {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String fileName;

    @Column
    private String fileUri;

    @Column
    private String fileExtension;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID", nullable = false)
    private Board board;

    public File(MultipartFile validatedFile, Board board) {
        this.fileName = validatedFile.getOriginalFilename();
        this.fileUri = "http://localhost:7777/api/file/" + board.getId() + "_" + this.fileName;
        this.fileExtension = this.fileName.substring(this.fileName.lastIndexOf(".") + 1);
        this.board = board;
    }


    public File(String filepath) {
    }
}