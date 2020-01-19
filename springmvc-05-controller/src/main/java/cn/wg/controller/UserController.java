package cn.wg.controller;

import cn.wg.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("user")
public class UserController {

    @GetMapping("t1")
    public String test(@RequestParam("username") String name, Model model) {
        //1.接受前端参数
        System.out.println("接受到了前端参数:" + name);
        //2.将返回的结果传递给前端，Model
        model.addAttribute("msg", name);
        //3.视图跳转
        return "test";
    }

    //前端接收的是一个对象，id，name，age
    /*
    1.接收前端用户传递的参数，判断参数饿名字，假设名字直接在方法上，可以直接使用
    2.假设传递的是一个User对象，匹配User对象中的字段名，如果名字一样则ok，否则，匹配不到

    */
    @GetMapping("t2")
    public String test2(User user) {
        System.out.println(user);
        return "test";
    }
}
