package com.example.web.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.poi.ss.usermodel.CellStyle;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户对象 sys_user
 * @ExcelProperty(index = 0,value = "")
 * 这个index属性一定要加上,不然会导致导入excel时获取不到数据
 * 别问我怎么知道的,  全是泪
 * 
 * @author ruoyi
 */
@Data
@TableName("sys_user")
public class SysUser extends BaseRowModel {
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    /**
     *
     */
    @TableId(value = "user_id",type = IdType.AUTO)
    private Long userId;

    /** 部门ID */
    private Long deptId;

    /** 部门父ID */
    @TableField(exist = false)
    private Long parentId;

    /** 登录名称 */
    @ExcelProperty(index = 0,value = "登录名称")
    private String loginName;

    /** 用户名称 */
    @ExcelProperty(index = 1, value = "用户名称")
    private String userName;

    /** 用户邮箱 */
    @ExcelProperty(index = 2, value = "用户邮箱")
    private String email;

    /** 手机号码 */
    @ExcelProperty(index = 3, value = "手机号码")
    private String phonenumber;

    /** 用户性别 */
    @ExcelProperty(index = 4, value = "用户性别(0: 男,1: 女,2: 未知)")
    private String sex;

    /** 用户头像 */
    private String avatar;

    /** 密码 */
    private String password;

    /** 盐加密 */
    private String salt;

    /** 帐号状态（0正常 1停用） */
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    @TableLogic
    private String delFlag;

    /** 最后登陆IP */
    private String loginIp;

    /** 最后登陆时间 */
    @ExcelProperty(index = 5, value = "最后登陆时间")
    private Date loginDate;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    @ExcelProperty(index = 6, value = "创建时间")
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(index = 7, value = "更新时间")
    private Date updateTime;

    /** 备注 */
    private String remark;

    @TableField(exist = false)
    private Map<Integer,CellStyle> cellStyleMap = new HashMap<>();

}
