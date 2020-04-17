package com.canzhen.persiondemo.inter.mapper;

import com.canzhen.persiondemo.bean.CommUser;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

/**
 * t_user 操作，继承 BaseMapper<T> 就可以了，是不是有点类似 JpaRepository
 */
@Mapper
public interface CommUserMapper extends BaseMapper<CommUser> {
    /**
     * 根据用户名统计（TODO 假设它是一个很复杂的SQL）
     *
     * @param username 用户名
     * @return 统计结果
     */
    int countByUsername(String username);

//    删除所有
    void deleteAll();
}
