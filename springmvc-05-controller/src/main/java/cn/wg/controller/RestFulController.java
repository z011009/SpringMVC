package cn.wg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RestFulController {

    //原来：http://localhost:8080/add?a=1&b=1
    //RestFul：
    //映射访问路径,必须是POST请求
    @RequestMapping(value = "add/{a}/{b}",method = RequestMethod.GET)
    public String test1(@PathVariable int a, @PathVariable int b, Model model) {
        int res = a + b;
        model.addAttribute("msg", "结果为:" + res);
        return "test";
    }
}
