package com.canzhen.persiondemo.inter.service;

import com.canzhen.persiondemo.bean.RedisUser;

public interface RedisUserService {
    /**
     * 修改或者新增
     *
     * @param user 用户对象
     * @return 操作结果
     */
    RedisUser saveOrUpdate(RedisUser user);

    /**
     * 获取
     *
     * @param id key值
     * @return 返回结果
     */
    RedisUser get(Long id);

    /**
     * 删除
     *
     * @param id key值
     */
    void delete(Long id);
}
