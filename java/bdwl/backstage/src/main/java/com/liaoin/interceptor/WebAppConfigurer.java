package com.liaoin.interceptor;

//import com.liaoin.constant.Constant;
//import com.liaoin.util.FileUtils;
import com.liaoin.resolvers.AdminArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.Resource;
import java.util.List;


@Configuration
public class WebAppConfigurer extends WebMvcConfigurerAdapter {


    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    @Resource
    private AdminArgumentResolver adminArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(authorizationInterceptor)
                .excludePathPatterns("/")
                .excludePathPatterns("/Login/doLogin")
        ;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(adminArgumentResolver);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        try {
//            String path = FileUtils.rootPath();
//            registry.addResourceHandler(Constant.PATH.FILE_UPLOAD_PATH +"/**").addResourceLocations("file:///"+path+"/"+Constant.PATH.FILE_UPLOAD_PATH+"/");
        }catch (Exception e){
        }

    }

}
