package com.kkh.board.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Upload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int fno;

    String filename;

    String fileOriName;

    String fileurl;

}