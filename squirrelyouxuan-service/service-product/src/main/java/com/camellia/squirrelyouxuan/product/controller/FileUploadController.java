package com.camellia.squirrelyouxuan.product.controller;

import com.camellia.squirrelyouxuan.common.result.Result;
import com.camellia.squirrelyouxuan.product.service.IFileUploadService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author fuyunjia
 * @Date 2023-11-08 15:58
 */

@Api(tags = "文件上传接口")
@RestController
@RequestMapping("admin/product")
public class FileUploadController {

    @Autowired
    private IFileUploadService fileUploadService;

    /**
     * 文件上传
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("fileUpload")
    public Result fileUpload(MultipartFile file) throws Exception{
        Result result = fileUploadService.fileUpload(file);

        return result;
    }

}
