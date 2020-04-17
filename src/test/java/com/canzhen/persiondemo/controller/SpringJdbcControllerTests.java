package com.canzhen.persiondemo.controller;

import com.canzhen.persiondemo.PersiondemoApplication;
import com.canzhen.persiondemo.bean.TemplateUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

//由于SpringJdbcController上面的接口是 restful 风格的接口，添加和修改无法通过浏览器完成，所以需要我们自己编写junit或者使用postman之类的工具。
//
//        创建单元测试Chapter4ApplicationTests，通过TestRestTemplate模拟GET、POST、PUT、DELETE等请求操作
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PersiondemoApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringJdbcControllerTests {
    private static final Logger log = LoggerFactory.getLogger(SpringJdbcControllerTests.class);
    @Autowired
    private TestRestTemplate template;
    @LocalServerPort
    private int port;
    @Test
    public void test1() throws Exception {

        template.postForEntity("http://localhost:" + port + "/persiondemo/jdbcusers", new TemplateUser("user1", "pass1"), Integer.class);
        log.info("[添加用户成功]\n");

        // TODO 如果是返回的集合,要用 exchange 而不是 getForEntity ，后者需要自己强转类型
        ResponseEntity<List<TemplateUser>> response2 = template.exchange("http://localhost:" + port + "/persiondemo/jdbcusers", HttpMethod.GET, null, new ParameterizedTypeReference<List<TemplateUser>>() {
        });
        final List<TemplateUser> body = response2.getBody();
        log.info("[查询所有] - [{}]\n", body);

        Long userId = body.get(0).getId();
        ResponseEntity<TemplateUser> response3 = template.getForEntity("http://localhost:" + port + "/persiondemo/jdbcusers/{id}", TemplateUser.class, userId);
        log.info("[主键查询] - [{}]\n", response3.getBody());

        template.put("http://localhost:" + port + "/persiondemo/jdbcusers/{id}", new TemplateUser("user11", "pass11"), userId);
        log.info("[修改用户成功]\n");

        template.delete("http://localhost:" + port + "/persiondemo/jdbcusers/{id}", userId);
        log.info("[删除用户成功]");

    }
}
