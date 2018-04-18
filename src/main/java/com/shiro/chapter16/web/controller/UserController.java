package com.shiro.chapter16.web.controller;

import com.shiro.chapter16.entity.User;
import com.shiro.chapter16.service.OrganizationService;
import com.shiro.chapter16.service.RoleService;
import com.shiro.chapter16.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * shiro注解
 * 1、@RequiresAuthentication 验证用户是否登陆
 * 验证用户是否登录等同于方法subject.isAuthenticated() 结果为true时
 *
 * 2、@RequiresUser 验证用户是否记住
 * 一种是成功登录的（subject.isAuthenticated() 结果为true）；
 * 另外一种是被记忆的（subject.isRemembered()结果为true）。
 *
 * 3、@RequiresGuest 验证用户是否为gust请求，与@RequiresUser完全相反
 * RequiresUser  == !RequiresGuest。此时subject.getPrincipal() 结果为null.
 *
 * 4、@RequiresRoles 验证是否有角色
 * 比如@RequiresRoles("aRoleName");void someMethod();
 * 如果subject中有aRoleName角色才可以访问方法someMethod。如果没有这个权限则会抛出异常AuthorizationException。
 *
 * 5、@RequiresPermissions 验证是否有权限
 *   例如： @RequiresPermissions({"file:read", "write:aFile.txt"} )
 *  void someMethod();
 *  要求subject中必须同时含有file:read和write:aFile.txt的权限才能执行方法someMethod()。否则抛出异常AuthorizationException。
 *
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired private UserService userService;
    @Autowired private OrganizationService organizationService;
    @Autowired private RoleService roleService;

    /**
     * 规则：“资源标识符：操作：对象实例 ID” 即对哪个资源的哪个实例可以进行什么操作。
     其默认支持通配符权限字符串，“:”表示资源/操作/实例的分割；“,”表示操作的分割；
     “*”表示任意资源/操作/实例
     * ":" 分割资源  "," 分割操作
     * system:user:update,delete 用户拥有system:user 的跟新和删除权限
     * 资源类型(user,role,resource)：操作(CRUD)：资源ID
     * user:edit:1001  用户拥有 修改id为1001的用户的 权限
     * resource:create 用户拥有 创建资源的权限(用户先找角色，通过角色找资源)
     * 相当于Subject currentUser = SecurityUtils.getSubject();
     *
     * a、判断示 if (currentUser.hasPermission("user:view")) {} else {}
     * b、断言式 currentUser.checkPermission("user:view“)
     * c、注解式 @RequiresPermissions("user:view")查询用户的权限
     */
    @RequiresPermissions("user:view")// 从权限集合中若包含"user:view"字段 表示拥有权限
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("userList", userService.findAll());
        return "user/list";
    }

    @RequiresPermissions("user:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String showCreateForm(Model model) {
        setCommonData(model);
        model.addAttribute("user", new User());
        model.addAttribute("op", "新增");
        return "user/edit";
    }

    @RequiresPermissions("user:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(User user, RedirectAttributes redirectAttributes) {
        userService.createUser(user);
        redirectAttributes.addFlashAttribute("msg", "新增成功");
        return "redirect:/user";
    }

    @RequiresPermissions("user:update")
    @RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        setCommonData(model);
        model.addAttribute("user", userService.findOne(id));
        model.addAttribute("op", "修改");
        return "user/edit";
    }

    @RequiresPermissions("user:update")
    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String update(User user, RedirectAttributes redirectAttributes) {
        userService.updateUser(user);
        redirectAttributes.addFlashAttribute("msg", "修改成功");
        return "redirect:/user";
    }

    @RequiresPermissions("user:delete")
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String showDeleteForm(@PathVariable("id") Long id, Model model) {
        setCommonData(model);
        model.addAttribute("user", userService.findOne(id));
        model.addAttribute("op", "删除");
        return "user/edit";
    }

    @RequiresPermissions("user:delete")
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/user";
    }


    @RequiresPermissions("user:update")
    @RequestMapping(value = "/{id}/changePassword", method = RequestMethod.GET)
    public String showChangePasswordForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findOne(id));
        model.addAttribute("op", "修改密码");
        return "user/changePassword";
    }

    @RequiresPermissions("user:update")
    @RequestMapping(value = "/{id}/changePassword", method = RequestMethod.POST)
    public String changePassword(@PathVariable("id") Long id, String newPassword, RedirectAttributes redirectAttributes) {
        userService.changePassword(id, newPassword);
        redirectAttributes.addFlashAttribute("msg", "修改密码成功");
        return "redirect:/user";
    }

    private void setCommonData(Model model) {
        model.addAttribute("organizationList", organizationService.findAll());
        model.addAttribute("roleList", roleService.findAll());
    }
}
