package com.leyou.upload.controller;

import com.leyou.upload.service.UploadService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author YYDCYY
 * @create 2019-10-24
 *
 * 图片上传需求分析 :
 * - 请求方式：上传肯定是POST
 * - 请求路径：/upload/image
 * - 请求参数：文件，参数名是file，SpringMVC会封装为：MultipartFile
 * - 返回结果：上传成功后得到的文件的url路径，也就是返回String

 */
@RestController
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    /**
     * 图片上传
     * @param file
     * @return
     */
    @PostMapping("image")
    public ResponseEntity <String> uploadImage(@RequestParam("file") MultipartFile file){
        //System.out.println(file.getName() + "  :: usr:: " ); // file? 搞笑
        String url = this.uploadService.upload(file);
        if (StringUtils.isBlank(url)) {
            return ResponseEntity.badRequest().build();
    }
        return ResponseEntity.status(HttpStatus.CREATED).body(url);
    }
}
