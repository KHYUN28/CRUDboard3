package com.kkh.board.repository;

import com.kkh.board.model.Upload;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileUploadRepository extends JpaRepository<Upload, Long> {

}

