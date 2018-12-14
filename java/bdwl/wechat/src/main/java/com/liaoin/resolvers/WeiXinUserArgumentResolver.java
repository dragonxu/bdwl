package com.liaoin.resolvers;

import com.liaoin.annotation.LoadWeiXinUser;
import com.liaoin.bean.WeiXinUser;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletRequest;

/**
 * 增加方法注入，将含有LoadWeiXinUser注解的方法参数注入当前登录用户
 */
@Component
public class WeiXinUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //如果参数类型是String并且有AnnotationUser注解则支持
        if (parameter.getParameterType().isAssignableFrom(WeiXinUser.class) && parameter.hasParameterAnnotation(LoadWeiXinUser.class))
            return true;
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        WeiXinUser weiXinUser = (WeiXinUser)request.getSession().getAttribute("weiXinUser");
        if (weiXinUser != null) {
            return weiXinUser;
        }
        throw new MissingServletRequestPartException("weiXinUser");
    }
}
