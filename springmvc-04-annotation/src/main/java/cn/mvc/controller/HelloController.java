package cn.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("hello")
public class HelloController {

    @RequestMapping("hello")
    public String sayhello(Model model){
        model.addAttribute("msg","HelloSpringMVC,Annotation");
        return  "hello";
    }

}
