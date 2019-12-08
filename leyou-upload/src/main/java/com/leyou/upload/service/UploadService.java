package com.leyou.upload.service;


import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author YYDCYY
 * @create 2019-10-24
 *
 * 文件上传 service 层需求分析:
 * 1. 校验文件大小
 * 2. 校验文件的媒体类型
 * 3. 校验文件的内容
 *
 * 文件大小在Spring的配置文件中设置，因此已经会被校验，我们不用管。
 */
@Service
public class UploadService {


        @Autowired
        private FastFileStorageClient storageClient;

        private static final List<String> CONTENT_TYPES = Arrays.asList("image/jpeg", "image/gif","image/png");

        private static final Logger LOGGER = LoggerFactory.getLogger(UploadService.class);

        public String upload(MultipartFile file) {

            String originalFilename = file.getOriginalFilename();
            // 校验文件的类型
            String contentType = file.getContentType();
            if (!CONTENT_TYPES.contains(contentType)){
                // 文件类型不合法，直接返回null
                LOGGER.info("文件类型不合法：{}", originalFilename);
                return null;
            }

            try {
                // 校验文件的内容
                BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
                if (bufferedImage == null){
                    LOGGER.info("文件内容不合法：{}", originalFilename);
                    return null;
                }

                // 保存到服务器
                // file.transferTo(new File("/Users/yuyang/Downloads/测试/zel.jpg" + originalFilename));
                String ext = StringUtils.substringAfterLast(originalFilename, ".");
                StorePath storePath = this.storageClient.uploadFile(file.getInputStream(), file.getSize(), ext, null);
               // System.out.println("UploadService.java : 地址 :: " + storePath.getFullPath());
                // 生成url地址，返回
                return "http://image.leyou.com/" + storePath.getFullPath();
            } catch (IOException e) {
                LOGGER.info("服务器内部错误：{}", originalFilename);
                e.printStackTrace();
            }
            return null;
        }
    }
