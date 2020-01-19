package cn.wugou.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginIntercepter implements HandlerInterceptor {
    //return true;执行下一个拦截器，放行
    //return false;不执行下一个拦截器，放行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session=request.getSession();
       /* //url中包含toLogin直接放行
        if(request.getRequestURI().contains("toLogin")){
            return true;
        }
        //url中包含login直接放行
        if(request.getRequestURI().contains("login")){
            return true;
        }*/
        //用户名不为空放行
        if(session.getAttribute("loginName")!=null){
            return true;
        }
        //没有登录跳转到登录页面
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request,response);
        return false;
    }
}
