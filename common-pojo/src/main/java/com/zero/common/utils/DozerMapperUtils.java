package com.zero.common.utils;

import org.apache.commons.collections.CollectionUtils;
import org.dozer.DozerBeanMapperBuilder;
import org.dozer.Mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jianqing.li
 * @date 2018/4/2
 */
public class DozerMapperUtils {

    private DozerMapperUtils() {}

    public static final Mapper mapper = DozerBeanMapperBuilder.create().build();

    /**
     * 复制对象属性
     * @param source 源对象
     * @param targetClass 目标class
     * @param <T>
     * @return
     */
    public static <T> T beanCopy(Object source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        return mapper.map(source, targetClass);
    }

    /**
     * 复制list
     * @param source 源数据集合
     * @param targetClass 目标数据集合泛型
     * @param <E>
     * @return
     */
    public static <E> List<E> beanCopy(Collection source, Class<E> targetClass) {
        if (CollectionUtils.isEmpty(source)) {
            return null;
        }
        return  (List<E>) source.stream().map(item -> mapper.map(item, targetClass)).collect(Collectors.toList());
    }
}
