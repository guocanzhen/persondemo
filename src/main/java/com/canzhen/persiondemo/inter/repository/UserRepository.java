package com.canzhen.persiondemo.inter.repository;

import com.canzhen.persiondemo.bean.JpaUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//t_user 操作
//创建UserRepository数据访问层接口，需要继承JpaRepository<T,K>，第一个泛型参数是实体对象的名称，第二个是主键类型
@Repository
public interface UserRepository extends JpaRepository<JpaUser,Long>{
    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 查询结果
     */
//    JPA支持@Query注解写HQL，也支持findAllByUsername这种根据字段名命名的方式
    List<JpaUser> findAllByUsername(String username);
}
