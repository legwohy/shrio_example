package com.shiro.chapter16.web.controller;

import com.shiro.chapter16.entity.Resource;
import com.shiro.chapter16.entity.User;
import com.shiro.chapter16.service.ResourceService;
import com.shiro.chapter16.service.UserService;
import com.shiro.chapter16.web.bind.annotation.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;

/**
 *
 */
@Controller
public class IndexController {

    @Autowired private ResourceService resourceService;
    @Autowired private UserService userService;

    /**
     *
     * @param loginUser 自定义参数绑定 参数值为{user.id,user.name}这种格式 @CurrentUser user
     * @param model
     * @return
     */
    @RequestMapping("/")
    public String index(@CurrentUser User loginUser, Model model) {
        Set<String> permissions = userService.findPermissions(loginUser.getUsername());
        List<Resource> menus = resourceService.findMenus(permissions);
        model.addAttribute("menus", menus);
        return "index";
    }

    @RequestMapping("/welcome")
    public String welcome() {
        return "welcome";
    }


}
