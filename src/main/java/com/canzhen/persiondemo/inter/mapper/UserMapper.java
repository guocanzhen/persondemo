package com.canzhen.persiondemo.inter.mapper;

import com.canzhen.persiondemo.bean.MybatisUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * t_user 操作：演示两种方式
 * <p>第一种是基于mybatis3.x版本后提供的注解方式<p/>
 * <p>第二种是早期写法，将SQL写在 XML 中<p/>
 */
@Mapper
public interface UserMapper {
    /**
     * 根据用户名查询用户结果集
     *
     * @param username 用户名
     * @return 查询结果
     */
    @Select("SELECT * FROM t_user WHERE username = #{username}")
    List<MybatisUser> findByUsername(@Param("username") String username);

    /**
     * 保存用户信息
     *
     * @param mybatisUser 用户信息
     * @return 成功 1 失败 0
     */
    int insert(MybatisUser mybatisUser);

    /**
     * 删除所有用户信息
     */
    void deleteAll();
}
