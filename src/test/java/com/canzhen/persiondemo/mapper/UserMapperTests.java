package com.canzhen.persiondemo.mapper;

import com.canzhen.persiondemo.bean.MybatisUser;
import com.canzhen.persiondemo.inter.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTests {
    private static final Logger log = LoggerFactory.getLogger(UserMapperTests.class);

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test1() throws Exception{
        final int row1 = userMapper.insert(new MybatisUser("u1", "p1"));
        log.info("[添加结果] - [{}]", row1);

        final int row2 = userMapper.insert(new MybatisUser("u2", "p2"));
        log.info("[添加结果] - [{}]", row2);

        final int row3 = userMapper.insert(new MybatisUser("u1", "p3"));
        log.info("[添加结果] - [{}]", row3);

        final List<MybatisUser> mybatisUsers = userMapper.findByUsername("u1");
        log.info("[根据用户名查询] - [{}]", mybatisUsers);

        userMapper.deleteAll();
        log.info("[删除所有用户密码]");

    }
}
