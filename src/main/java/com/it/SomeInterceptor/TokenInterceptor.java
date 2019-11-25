package com.it.SomeInterceptor;

import com.it.Util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class TokenInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String origin = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers","Origin,Content-Type,Accept,token,X-Requested-With");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setCharacterEncoding("utf-8");//设置跨域
        if(request.getMethod().equals("OPTIONS")){//过滤请求
            return false;
        }
//        String token = request.getHeader("token");
        String token = request.getParameter("token");
        String id=request.getParameter("id");
//        System.out.println(token);
//        System.out.println("id是"+id);
        if (token != null){
            boolean result = JwtUtil.verify(token);//有效为真，过期为假
            if(result){
                System.out.println("通过拦截器");//成功登陆 ，请求一次登陆者名字，再请求一次登录者想要的信息,所以打印了两次
                return true;
            }
        }
        System.out.println("认证失败");
        response.getWriter().write("50000");//令牌为空或者过期都返回一个50000
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
