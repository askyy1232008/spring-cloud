package com.cloud.zuul.config;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
 
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
 
/**
 * 路径：com.example.demo.zuul.config
 * 类名：
 * 功能：过滤器
 * 备注：
 * 创建人：typ
 * 创建时间：2018/9/13 14:12
 * 修改人：
 * 修改备注：
 * 修改时间：
 */
 
@Component
public class MyFilter extends ZuulFilter {
 
    private static final Logger log = LoggerFactory.getLogger(MyFilter.class);
 
    /**
     * 方法名：filterType
     * 功能：返回一个字符串代表过滤器的类型
     * 描述：
     *     pre：路由之前
     *     routing：路由之时
     *     post： 路由之后
     *     error：发送错误调用
     * 创建人：typ
     * 创建时间：2018/9/13 14:23
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    @Override
    public String filterType() {
        return "pre";
    }
 
    /**
     * 方法名：filterOrder
     * 功能：过滤的顺序
     * 描述：
     * 创建人：typ
     * 创建时间：2018/9/13 14:24 
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    @Override
    public int filterOrder() {
        return 0;
    }
 
    /**
     * 方法名：shouldFilter
     * 功能：这里可以写逻辑判断，是否要过滤，本文true,永远过滤。
     * 描述：
     * 创建人：typ
     * 创建时间：2018/9/13 14:25 
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }
 
    /**
     * 方法名：run
     * 功能：过滤器的具体逻辑。
     * 描述：
     * 创建人：typ
     * 创建时间：2018/9/13 14:25
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        log.info("method：{}, url：{}",request.getMethod(),request.getRequestURL());
        Object token = request.getParameter("token");
        if(token == null){
            log.info("token is empty");
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(401);
            try {
                context.getResponse().getWriter().write("token is empty");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        log.info("ok");
        return null;
    }
}
