package com.information.staff.controller;

import com.information.staff.service.OssService;
import com.information.staff.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/oss/fileoss")
@CrossOrigin
public class OssController {
    @Autowired
    private OssService ossService;
    //上传头像方法
    @PostMapping("uploadOssFile")
    public R uploadOssFile(MultipartFile file) {
        //获取上传文件 MultipartFile
        String url = ossService.uploadFileAvatar(file);
        return R.ok().data("url",url);
    }
}