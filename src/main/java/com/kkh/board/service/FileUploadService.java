package com.kkh.board.service;

import com.kkh.board.dto.FileUploadDto;
import com.kkh.board.repository.FileUploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileUploadService {
    @Autowired
    private FileUploadRepository fileUploadRepository;

    public List<FileUploadDto> processFileUpload(MultipartHttpServletRequest request) {
        List<FileUploadDto> uploadedFiles = new ArrayList<>();

        FileUploadDto fileDTO = new FileUploadDto();
        uploadedFiles.add(fileDTO);

        return uploadedFiles;
    }
}
