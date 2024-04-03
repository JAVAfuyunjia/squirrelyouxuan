package com.camellia.squirrelyouxuan.acl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.camellia.squirrelyouxuan.model.acl.Admin;

import java.util.HashMap;

/**
 * @Author fuyunjia
 * @Date 2023-11-05 14:12
 */
public interface IAdminService extends IService<Admin> {




    /**
     * 并且获取token
     * @param admin
     * @return
     */
    String login(Admin admin);

    /**
     * 获取管理端首页信息
     * @return
     */
    HashMap<String, Object> getDashboard();
}
