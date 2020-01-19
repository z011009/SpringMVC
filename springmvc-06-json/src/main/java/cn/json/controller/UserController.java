package cn.json.controller;

import cn.json.pojo.User;
import cn.json.utils.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class UserController {
    @RequestMapping("j1")
    public String json1() throws JsonProcessingException {
        //创建一个对象
        User user = new User("wugou", 18, "男");
        return JSONUtil.getJson(user);
    }

    @RequestMapping("j2")
    public String json2() {
        //创建一个对象
        List<User> users = new ArrayList<User>();
        User user1 = new User("wugou1", 18, "男");
        User user2 = new User("wugou2", 18, "男");
        User user3 = new User("wugou3", 18, "男");
        users.add(user1);
        users.add(user2);
        users.add(user3);
        return JSONUtil.getJson(users);
    }

    @RequestMapping("j3")
    public String json3() {
        Date date = new Date();
        return JSONUtil.getJson(date);
        //return JSONUtil.getJson(date,"yyyy-MM-dd");//自定义时间格式
    }
}
