package cn.wugou.controller;

import cn.wugou.pojo.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AjaxController {
    @RequestMapping("t1")
    public String test() {
        return "hello";
    }

    @RequestMapping("a1")
    public void a1(String name, HttpServletResponse response) throws IOException {
        System.out.println("name:"+name);
        if("wugou".equals(name)){
            response.getWriter().print(true);
        }else{
            response.getWriter().print(false);
        }
    }

    @RequestMapping("a2")
    public List<User> a2(){
        List<User> users = new ArrayList<>();
        users.add(new User("wugou1",1,"男"));
        users.add(new User("wugou2",1,"男"));
        users.add(new User("wugou3",1,"男"));
        return users;
    }

    @RequestMapping("a3")
    public String a3(String name,String pwd){
        String msg="";
        if(name!=null){
            if("admin".equals(name)){
                msg="ok";
            }else{
                msg="用户名有误";
            }
        }
        if(pwd!=null){
            if("123456".equals(pwd)){
                msg="ok";
            }else{
                msg="密码有误";
            }
        }
        return msg;
    }
}
