package cn.wg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EncodingController {
    @GetMapping("toForm")
    public String toForm(){
        return "from";
    }
    @PostMapping("test")
    public String Test(Model model,String name){
        model.addAttribute("msg",name);
        return "test";
    }
}
