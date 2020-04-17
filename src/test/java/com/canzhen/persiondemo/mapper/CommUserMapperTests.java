package com.canzhen.persiondemo.mapper;

import com.canzhen.persiondemo.bean.CommUser;
import com.canzhen.persiondemo.inter.mapper.CommUserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommUserMapperTests {
    private static final Logger log = LoggerFactory.getLogger(CommUserMapperTests.class);

    @Autowired
    private CommUserMapper commUserMapper;

    @Test
    public void test1() throws Exception {
        final CommUser commUser1 = new CommUser("u1", "p1");
        final CommUser commUser2 = new CommUser("u1", "p2");
        final CommUser commUser3 = new CommUser("u3", "p3");

        commUserMapper.insertSelective(commUser1);
        log.info("[user1回写主键] - [{}]", commUser1.getId());
        commUserMapper.insertSelective(commUser2);
        log.info("[user2回写主键] - [{}]", commUser2.getId());
        commUserMapper.insertSelective(commUser3);
        log.info("[user3回写主键] - [{}]", commUser3.getId());

        final int count = commUserMapper.countByUsername("u1");
        log.info("[调用自己写的SQL] - [{}]", count);

        // TODO 模拟分页
        for (int i = 0; i <= 20; i++) {
            commUserMapper.insertSelective(new CommUser("u1", "p1"));
        }

        // TODO 分页 + 排序 this.userMapper.selectAll() 这一句就是我们需要写的查询，有了这两款插件无缝切换各种数据库
        final PageInfo<Object> pageInfo = PageHelper.startPage(1, 10).setOrderBy("id desc").doSelectPageInfo(() -> this.commUserMapper.selectAll());
        log.info("[lambda写法] - [分页信息] - [{}]", pageInfo.toString());

        PageHelper.startPage(1, 10).setOrderBy("id desc");
        final PageInfo<CommUser> userPageInfo = new PageInfo<>(this.commUserMapper.selectAll());
        log.info("[普通写法] - [{}]", userPageInfo);

//        删除所有用户密码
        commUserMapper.deleteAll();
        log.info("删除所有用户密码");
    }
}
