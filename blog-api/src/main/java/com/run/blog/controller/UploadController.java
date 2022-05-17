package com.run.blog.controller;

import com.run.blog.utils.OssUtil;
import com.run.blog.vo.ErrorCode;
import com.run.blog.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author AruNi_Lu
 * @data 2022/4/12
 */
@RestController
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private OssUtil ossUtil;

    @PostMapping
    // image是前端传过来的参数(类型是file，SPB里面用MultipartFile来接收文件类型)
    public Result upload(@RequestParam("image")MultipartFile file) {
        // 原始文件名，例如：abc.png
        String originalFileName = file.getOriginalFilename();

        // 上传到图片服务器，文件名称必须唯一，所以要赋予新的文件名
        // substringAfterLast是获取原文件名中 最后一个以'.'分割的后半部分，即 'png'
        String time = String.valueOf(System.currentTimeMillis());
        String fileName = time + "." + StringUtils.substringAfterLast(originalFileName, ".");

        // 上传到OSS，上传成功后返回url
        String uploadUrl = ossUtil.upload(file, fileName, "article_img");
        if (uploadUrl != null) {
            return Result.success(uploadUrl);
        }
        return Result.fail(ErrorCode.UPLOAD_FAIL.getCode(), ErrorCode.UPLOAD_FAIL.getMsg());

    }
}
