package com.liaoin.resolvers;

import com.liaoin.annotation.LoadGoods;
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


@Component
public class GoodsArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //如果参数类型是String并且有AnnotationUser注解则支持
        if (parameter.getParameterType().isAssignableFrom(String.class) && parameter.hasParameterAnnotation(LoadGoods.class))
            return true;
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String goodsId = (String)request.getSession().getAttribute("goodsId");
        if (goodsId != null) {
            return goodsId;
        }
        throw new MissingServletRequestPartException("goodsId");
    }
}
