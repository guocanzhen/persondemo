package com.canzhen.persiondemo.repository;

import com.canzhen.persiondemo.bean.JpaUser;
import com.canzhen.persiondemo.inter.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTests {
    private static final Logger log = LoggerFactory.getLogger(UserRepositoryTests.class);

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test1() throws Exception {
        final JpaUser jpaUser = userRepository.save(new JpaUser("u1", "p1"));
        log.info("[添加成功] - [{}]", jpaUser);

        final List<JpaUser> jpaUserList = userRepository.findAllByUsername("u1");
        log.info("[条件查询] - [{}]", jpaUserList);

        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("username")));
        final Page<JpaUser> jpaUsers = userRepository.findAll(pageable);
        log.info("[分页+排序+查询所有] - [{}]", jpaUsers.getContent());

        userRepository.findById(jpaUsers.getContent().get(0).getId()).ifPresent(user1 -> log.info("[主键查询] - [{}]", user1));

        final JpaUser edit = userRepository.save(new JpaUser(jpaUser.getId(), "修改后的ui", "修改后的p1"));
        log.info("[修改成功] - [{}]", edit);

        userRepository.deleteById(jpaUser.getId());
        log.info("[删除主键为 {} 成功] - [{}]", jpaUser.getId());

        userRepository.deleteAll();
        log.info("删除所有用户密码");
    }
}
