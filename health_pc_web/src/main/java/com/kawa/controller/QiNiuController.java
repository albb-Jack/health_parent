package com.kawa.controller;

import com.kawa.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/qiniu")
public class QiNiuController {


    @Autowired
    private QiniuUtils qiniuUtils;


    /**
     * 上传文件
     */
    @GetMapping("/upload")
    public String upload() {

        String path = "/Users/kuli/261699586235_.pic_hd.jpg";

        String fileName = UUID.randomUUID().toString();

        qiniuUtils.upload2Qiniu(path, fileName + ".jpg");


        return qiniuUtils.domainName + fileName + ".jpg";
    }


}
