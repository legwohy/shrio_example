package com.shiro.chapter16.web.bind.method;

import com.shiro.chapter16.web.bind.annotation.CurrentUser;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * <p>用于绑定@FormModel的方法参数解析器 xml中需要开启解析器
 *
 */
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    public CurrentUserMethodArgumentResolver() {}

    /**
     * 看有没有 @CurrentUser这个注解
     * 用于判定是否需要处理该参数分解，返回true为需要，并会去调用下面的方法resolveArgument。 返回true需要
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 判断是否支持要转换的参数类型
        if (parameter.hasParameterAnnotation(CurrentUser.class)) {
            return true;
        }
        return false;
    }

    /**
     *
     * 真正用于处理参数分解的方法，返回的Object就是controller方法上的形参对象。
     * @param parameter
     * @param mavContainer
     * @param webRequest
     * @param binderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 当支持后进行相应的转换
        CurrentUser currentUserAnnotation = parameter.getParameterAnnotation(CurrentUser.class);
        return webRequest.getAttribute(currentUserAnnotation.value(), NativeWebRequest.SCOPE_REQUEST);
    }
}
