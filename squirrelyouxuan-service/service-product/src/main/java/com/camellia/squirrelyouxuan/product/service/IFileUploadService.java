package com.camellia.squirrelyouxuan.product.service;

import com.camellia.squirrelyouxuan.common.result.Result;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author fuyunjia
 * @Date 2023-11-08 15:59
 */
public interface IFileUploadService {

    /**
     * 文件上传方法
     * @param file
     * @return
     * @throws Exception
     */
    Result fileUpload(MultipartFile file) throws Exception;
}
