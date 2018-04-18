package com.shiro.chapter16.web.controller;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 登陆控制器
 */
@Controller
public class LoginController {

    /**
     * 表单认证 xml需要配置FormAuthenticationFilter 登陆失败
     * DisabledAccountException （禁用的帐号）
     * LockedAccountException （锁定的帐号）
     * UnknownAccountException（错误的帐号）
     * ExcessiveAttemptsException（登录失败次数过多）
     * IncorrectCredentialsException （错误的凭证）
     * ExpiredCredentialsException （过期的凭证）
     * @param req
     * @param model
     * @return
     */
    @RequestMapping(value = "/login")
    public String showLoginForm(HttpServletRequest req, Model model) {
        String exceptionClassName = (String)req.getAttribute("shiroLoginFailure");// 取出异常的名字
        String error = null;
        if(UnknownAccountException.class.getName().equals(exceptionClassName)) {// shiro 提供的异常主要是凭证和账户异常两个方面
            error = "用户名/密码错误";// admin 123456
        } else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if(exceptionClassName != null) {
            error = "其他错误：" + exceptionClassName;
        }
        model.addAttribute("error", error);
        return "login";
    }


}
