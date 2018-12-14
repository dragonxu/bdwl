package com.liaoin.filter;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.liaoin.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
@Slf4j
@SuppressWarnings("all")
public class ApiFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("----过滤器初始化----");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //访问日志
        Map<String, String[]> map = request.getParameterMap();
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            try {
                jsonObject.put(entry.getKey(), entry.getValue());
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        log.info("来访者IP：" + request.getRemoteHost() + ",访问的URI：" + request.getRequestURI() + ",请求参数：" + jsonObject.toString());
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.info("----过滤器销毁----");
    }
}
