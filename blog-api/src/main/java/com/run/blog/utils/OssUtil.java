package com.run.blog.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @author AruNi_Lu
 * @data 2022/4/12
 */
@Component
public class OssUtil {
    // Endpoint以北京为例，其它Region请按实际情况填写。
    String endpoint = "https://oss-cn-beijing.aliyuncs.com";
    String accessKeyId = "";
    String accessKeySecret = "";
    String bucketName = "";

    /**
     * <p>上传图片</p>
     * @param file 原图片
     * @param fileName 图片的文件名
     * @param objectName OSS的bucket中的文件夹目录名
     * @return
     */
    public String upload(MultipartFile file, String fileName, String objectName) {
        // 创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 图片路径
        String fileUrl = objectName + "/" + fileName;
        try {
            // 获取该图片的字节流
            byte[] uploadBytes = file.getBytes();
            // 创建PutObject请求
            ossClient.putObject(bucketName, fileUrl, new ByteArrayInputStream(uploadBytes));

            // 返回OSS中该图片的URL(拼接方式)
            String uploadUrl = "https://" + bucketName + "." + StringUtils.substringAfter(endpoint, "//") + "/" + fileUrl;
            return uploadUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}
