package com.shiro.chapter16.service;

import com.shiro.chapter16.entity.User;

import java.util.List;
import java.util.Set;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public interface UserService {

    /**
     * 创建用户
     * @param user
     */
    User createUser(User user);

    User updateUser(User user);

    void deleteUser(Long userId);

    /**
     * 修改密码
     * @param userId
     * @param newPassword
     */
    void changePassword(Long userId, String newPassword);


    User findOne(Long userId);

    List<User> findAll();

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * {@link com.shiro.chapter16.entity.Role}
     * 根据用户名查找其角色 返回role.getRole 比如 Chairman
     * @param username
     * @return
     */
    Set<String> findRoles(String username);

    /**
     * {@link com.shiro.chapter16.entity.Resource}
     * 根据用户名查找其权限 权限resource.getPermission 比如 organization:create
     * @param username
     * @return
     */
    Set<String> findPermissions(String username);

}
