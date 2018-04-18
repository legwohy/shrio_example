package com.shiro.chapter16.web.shiro.filter;

import com.shiro.chapter16.Constants;
import com.shiro.chapter16.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * url路径过滤器
 */
public class SysUserFilter extends PathMatchingFilter {

    @Autowired private UserService userService;

    /**
     * onPreHandle：在preHandle中，当pathsMatch匹配一个路径后，
     * 会调用opPreHandler方法并将路径绑定参数配置传给mappedValue；
     * 然后可以在这个方法中进行一些验证（如角色授权），如果验证失败可以返回false中断流程；默认返回true；
     * 也就是说子类可以只实现onPreHandle即可，无须实现preHandle。如果没有path与请求路径匹配，默认是通过的（即preHandle返回true）。
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        String username = (String)SecurityUtils.getSubject().getPrincipal();
        request.setAttribute(Constants.CURRENT_USER, userService.findByUsername(username));// 每一个路径均传递用户名作为参数
        return true;
    }
}
