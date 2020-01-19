package cn.wugou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("user")
public class LoginController {
    @RequestMapping("main")
    public String main(){
        return "main";
    }
    @RequestMapping("toLogin")
    public String login(){
        return "login";
    }
    @RequestMapping("login")
    public String login(String username, String password, HttpSession session, Model model){
        //把用户得到信息存在session
        session.setAttribute("loginName",username);
        //model.addAttribute("username",username);
        return "success";
    }

    @RequestMapping("logout")
    public String logout(HttpSession session){
        session.removeAttribute("loginName");
        return "login";
    }
}
