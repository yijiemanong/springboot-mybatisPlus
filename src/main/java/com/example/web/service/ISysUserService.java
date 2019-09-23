package com.example.web.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.model.SysUser;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Map;

/**
 * 用户 业务层
 * 
 * @author ruoyi
 */
public interface ISysUserService extends IService<SysUser> {
    /**
     * 根据条件分页查询用户列表
     *
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUserList();

    @Async
    void asyncTest();

    /**
     *
     * @param userList 数据
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName);

}
