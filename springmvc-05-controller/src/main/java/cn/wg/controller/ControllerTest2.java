package cn.wg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ControllerTest2 {

    @RequestMapping("t1")
    public String Hello(Model model){
        model.addAttribute("msg","HelloSpringMVC2");
        return "test";
    }
}
