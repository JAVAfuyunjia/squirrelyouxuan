package com.camellia.squirrelyouxuan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author fuyunjia
 * @Date 2023-11-04 15:29
 * Description 权限管理模块启动类
 */

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class ServiceAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceAuthApplication.class, args);
    }

}