package com.canzhen.persiondemo.controller;

import com.canzhen.persiondemo.bean.TemplateUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//使用JdbcTemplate进行访问数据库操作，这点是不规范的，各位别学我…
//restful 风格接口
@RestController
@RequestMapping("/jdbcusers")
public class SpringJdbcController {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SpringJdbcController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    public List<TemplateUser> queryUsers() {
        // 查询所有用户
        String sql = "select * from t_user";
        return jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<>(TemplateUser.class));
    }

    @GetMapping("/{id}")
    public TemplateUser getUser(@PathVariable Long id) {
        // 根据主键ID查询
        String sql = "select * from t_user where id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(TemplateUser.class));
    }

    @DeleteMapping("/{id}")
    public int delUser(@PathVariable Long id) {
        // 根据主键ID删除用户信息
        String sql = "DELETE FROM t_user WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @PostMapping
    public int addUser(@RequestBody TemplateUser templateUser) {
        // 添加用户
        String sql = "insert into t_user(username, password) values(?, ?)";
        return jdbcTemplate.update(sql, templateUser.getUsername(), templateUser.getPassword());
    }

    @PutMapping("/{id}")
    public int editUser(@PathVariable Long id, @RequestBody TemplateUser templateUser) {
        // 根据主键ID修改用户信息
        String sql = "UPDATE t_user SET username = ? ,password = ? WHERE id = ?";
        return jdbcTemplate.update(sql, templateUser.getUsername(), templateUser.getPassword(), id);
    }
}
