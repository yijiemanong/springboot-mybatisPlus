package com.example.web.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.web.model.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 用户表 数据层
 * 
 * @author ruoyi
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 根据条件分页查询用户列表
     *
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUserList();

}
