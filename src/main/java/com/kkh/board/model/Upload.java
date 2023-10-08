package com.kkh.board.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
public class Upload {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @ManyToOne
    private Board board;

    @ManyToOne
    private User user;

    String orgFileName;

    String sysFileName;

    @CreationTimestamp
    private Timestamp createDate;

}
