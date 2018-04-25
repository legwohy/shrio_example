package com.shiro.chapter16.service.impl;

import com.alibaba.druid.util.StringUtils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;

/**
 * session 必须是shiro中的session 以防侵入式的危害
 */
@Configuration
public class ShiroSessionHelp {
    @Autowired private static SessionDAO sessionDAO;


    /** 删除session  sessionId不同 删除会话*/
    public  boolean removeSession(String userName){
        boolean flag = false;
        Session session = getSessionByUserName(userName);// 要剔除用户的session
        if(null != session && StringUtils.equals(session.getId().toString(),getCurrentSessionId())){
            // 从缓存中删除用户
            session.stop();// 会话失效
            flag = true;
        }
        return flag;
    }

    /**
     * 通过用户名找到session
     *
     * 用户登陆成功后，将会将用户名存在 session 的 attribute 属性中,
     * key为 DefaultSubjectContext.PRINCIPALS_SESSION_KEY
     *
     */
    public  Session getSessionByUserName(String userName){
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session:sessions){
            if(null != session && StringUtils.equals(DefaultSubjectContext.PRINCIPALS_SESSION_KEY,userName)){
                return session;
            }
        }
        return null;

    }

    /** 当前用户的sessionId*/
    public  String getCurrentSessionId(){
        Session session = getCurrentSession();
        if(null == session){return null;}
        return session.getId().toString();
    }

    /** 获取当前session*/
    public static Session getCurrentSession(){return getSubject().getSession();}

    /** 用户已经认证通过*/
    public static boolean isAuthenticated(){return getSubject().isAuthenticated();}

    /** 获取当前用户*/
   private static Subject getSubject(){return SecurityUtils.getSubject();}


}
