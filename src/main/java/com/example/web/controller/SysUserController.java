package com.example.web.controller;


import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.web.domain.FebsResponse;
import com.example.web.exception.ExcelException;
import com.example.web.model.SysUser;
import com.example.web.service.ISysUserService;
import com.example.web.util.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 用户信息
 * 
 * @author ruoyi
 */
@RestController
@Slf4j
public class SysUserController {

    @Autowired
    private ISysUserService userService;


    @GetMapping("/list")
    public Map<String,Object> list() {
        // 默认查询 master库
        List<SysUser> list = userService.list();
        // 查询slave库
        List<SysUser> list2 = userService.selectUserList();
        Map<String,Object> map = new HashMap<>();
        map.put("list1",list);
        map.put("list2",list2);
        return map;
    }

    /**
     * 可以使用postMan模拟post请求
     * 测试发现id自增,createTime和updateTime均已有值
     * @return
     */

    @PostMapping("/addUser")
    public Map<String,Object> addUser() {
        SysUser sysUser = new SysUser();
        sysUser.setLoginName("zhangsan");
        sysUser.setUserName("张三");
        sysUser.setPassword("29c67a30398638269fe600f73a054934");
        Map<String,Object> map = new HashMap<>();
        map.put("msg","添加成功");
        try {
            userService.save(sysUser);
        }catch (Exception e) {
            map.put("msg","添加失败");
        }
        return map;
    }


    /**
     * 删除测试,看实体类中@TableLogic private String delFlag;是否生效
     *
     */
    @DeleteMapping("/del/{id}")
    public Map<String,Object> del(@PathVariable Long id) {
        Map<String,Object> map = new HashMap<>();
        map.put("msg","删除成功");
        try {
            userService.removeById(id);
        }catch (Exception e) {
            map.put("msg","删除失败");
        }
        return map;
    }

    /**
     * 最后测试一下分页
     *
     */
    @GetMapping("/listPage")
    public Map<String,Object> listPage() {
        Map<String,Object> map = new HashMap<>();
        IPage<SysUser> page = userService.page(new Page<>(1, 5));
        map.put("list",page);
        return map;
    }

    /**
     * 补一个更新
     *
     */

    @PutMapping("user")
    public Map<String,Object> updateDept(SysUser user) {
        Map<String,Object> map = new HashMap<>();
        boolean b = userService.updateById(user);
        if (b) {
            map.put("msg", "更新成功");
        }else {
            map.put("msg", "更新失败");
        }
        return map;
    }

    @GetMapping("hello")
    public String testAsyncNoRetrun(){
        long start = System.currentTimeMillis();
        // userService.asyncTest()方法上加@Async  耗时6ms
        // userService.asyncTest()方法上不加@Async  耗时3008ms
        userService.asyncTest();
        return String.format("任务执行成功,耗时{%s}毫秒", System.currentTimeMillis() - start);
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        try {
            List<SysUser> list = userService.selectUserList();
            list.forEach(x -> {
                if ("0".equals(x.getSex())) {
                    x.setSex("男");
                } else if ("1".equals(x.getSex())) {
                    x.setSex("女");
                } else if ("2".equals(x.getSex())) {
                    x.setSex("未知");
                }
            });
            ExcelUtil.writeExcel(response,list,"用户信息表","用户信息表",ExcelTypeEnum.XLSX,SysUser.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/importExcel")
    public FebsResponse importExcel(MultipartFile file) {
        FebsResponse febsResponse = new FebsResponse();
        if (file.isEmpty()) {
            return febsResponse.message("请选择文件！");
        }
        String result;
        try {
            List<SysUser> userList = ExcelUtil.readExcel(file,SysUser.class);
            // TODO 登陆名称,这里暂时写死
            String loginName = "zjh";
            // 导入到数据库中  设置为false,默认不修改已存在的数据,该属性应该前台传过来
            result = userService.importUser(userList, false, loginName);
        } catch (ExcelException e) {
            log.info(e.getMessage());
            return febsResponse.message(""+e.getMessage());
        }
        return febsResponse.message(result);
    }

}