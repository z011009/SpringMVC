package cn.wg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ModelAndViewTest {

    @RequestMapping("/m1/t1")
    public String test1() {
        return "redirect:/hello";
    }

    @RequestMapping("hello")
    public String hello() {
        return "test";
    }
}
