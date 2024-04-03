package com.camellia.squirrelyouxuan.product.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.camellia.squirrelyouxuan.common.result.Result;
import com.camellia.squirrelyouxuan.product.service.IFileUploadService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @Author fuyunjia
 * @Date 2023-11-08 16:01
 */
@Service
public class FileUploadServiceImpl implements IFileUploadService
{
    @Value("${aliyun.endpoint}")
    private String endPoint;
    @Value("${aliyun.keyid}")
    private String accessKey;
    @Value("${aliyun.keysecret}")
    private String secreKey;
    @Value("${aliyun.bucketname}")
    private String bucketName;

    @Override
    public Result fileUpload(MultipartFile file) throws Exception {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder()
                .build(endPoint, accessKey, secreKey);
        try {
            // 上传文件输入流
            InputStream inputStream = file.getInputStream();

            // 获取文件实际名称
            String objectName = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            objectName = uuid+objectName;

            //对上传文件进行分组，根据当前年/月/日
            // objectName:  2023/10/10/uuid01.jpg
            String currentDateTime = new DateTime().toString("yyyy/MM/dd");
            objectName = currentDateTime+"/"+objectName;

            // 创建PutObjectRequest对象
            //第一个 bucket名称
            //第二个 上传文件路径+名称
            /// 比如只有名称 01.jpg
            /// 比如 /aa/bb/001.jpg
            //第三个 文件输入流
            PutObjectRequest putObjectRequest =
                    new PutObjectRequest(bucketName, objectName, inputStream);
            // 设置该属性可以返回response。如果不设置，则返回的response为空。
            putObjectRequest.setProcess("true");
            // 创建PutObject请求。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            // 如果上传成功，则返回200。
            //返回上传图片在阿里云路径
            String url = result.getResponse().getUri();
            return Result.ok(url);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail(null);
        }finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}
