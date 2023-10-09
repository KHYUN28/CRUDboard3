package com.kkh.board.controller.view;

import com.kkh.board.model.Upload;
import com.kkh.board.model.User;
import com.kkh.board.repository.UploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

@Controller
public class UploadController {

    @Autowired
    private UploadRepository uploadRepository;

    @RequestMapping(value="/fileUpload")
    public String fileUploadTest(MultipartHttpServletRequest request, Model model, User user) {

        String rootUploadDir = "C:"+ File.separator+"A_Upload"; // C:/Upload

        File dir = new File(rootUploadDir + File.separator);

        if(!dir.exists()) { //업로드 디렉토리가 존재하지 않으면 생성
            dir.mkdirs();
        }

        Iterator<String> iterator = request.getFileNames(); //업로드된 파일정보 수집(2개 - file1,file2)

        int fileLoop = 0;
        String uploadFileName;
        MultipartFile mFile = null;
        String orgFileName = ""; //진짜 파일명
        String sysFileName = ""; //변환된 파일명

        ArrayList<String> list = new ArrayList<String>();

        while(iterator.hasNext()) {
            fileLoop++;

            uploadFileName = iterator.next();
            mFile = request.getFile(uploadFileName);

            orgFileName = mFile.getOriginalFilename();
            System.out.println(orgFileName);

            if(orgFileName != null && orgFileName.length() != 0) { //sysFileName 생성
                System.out.println("if문 진입");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss-" + fileLoop);
                Calendar calendar = Calendar.getInstance();
                sysFileName = simpleDateFormat.format(calendar.getTime()) + getFileExtension(orgFileName);

                try {
                    System.out.println("try 진입");
                    mFile.transferTo(new File(dir + File.separator + sysFileName));
                    list.add("원본 파일명: " + orgFileName + " || 저장된 파일명: " + sysFileName);

                }catch(Exception e){
                    list.add("파일 업로드 중 에러발생");
                }
            }
        }

        Upload upload = new Upload();
        upload.setOrgFileName(orgFileName);
        upload.setSysFileName(sysFileName);
        upload.setCreateDate(new Timestamp(System.currentTimeMillis()));

        uploadRepository.save(upload);

        model.addAttribute("list", list);

        return "board/saveForm";
    }

    private String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex != -1) {
            return fileName.substring(lastDotIndex);
        }
        return "";
    }
}
