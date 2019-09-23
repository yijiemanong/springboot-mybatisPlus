package com.example.web.util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class MyBatisUtils {

    /**
     * 将对象的非空属性转为Map结构的参数返回
     * 默认只访问父类的属性,不访问子类的属性
     *
     * @param obj 要转换的对象
     * @return
     */
    public static Map<String, Object> convertQueryParams(Object obj) {
        return convertQueryParams(obj, true);
    }

    /**
     * 将对象的非空属性转为Map结构的参数返回
     * 默认不访问子类的属性，只访问父类的属性
     *
     * @param obj          要转换的对象
     * @param isSuperClass 是否只转换父类中的属性
     * @return
     */
    public static Map<String, Object> convertQueryParams(Object obj, boolean isSuperClass) {
        Map<String, Object> data = new HashMap<>();
        // 转换父类中的属性
        Field[] superDeclaredFields = obj.getClass().getSuperclass().getDeclaredFields();
        data = convertQueryParams(superDeclaredFields, data, obj);

        if (isSuperClass) {
            return data;
        }
        // 转换子类中的属性
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        return  convertQueryParams(declaredFields, data, obj);
    }

    /**
     * 转换参数
     *
     * @param declaredFields 属性数组
     * @param data           存放数据的Map对象
     * @param obj            实例对象
     * @return
     */
    private static Map<String, Object> convertQueryParams(Field[] declaredFields, Map<String, Object> data, Object obj) {
        try {
            for (Field field : declaredFields) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }

                String key = field.getName();
                Object value = field.get(obj);

                // TODO 如果有自动生成的serialVersionUID，则不转换
                if ("serialVersionUID".equals(key)) {
                    continue;
                }

                if (value != null) {
                    key = StringUtils.camelToUnderline(key);
                    data.put(key, value);
                }

                System.out.println(field.getName());
            }
        } catch (IllegalAccessException e) {
            log.error(e.getMessage());
        }
        return data;
    }
}
